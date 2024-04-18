package admin;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class TransactionNotification implements RequestHandler<SQSEvent, Void> {

    private final String FROM_EMAIL; // Verified email in SES
    private final String RDS_URL; // RDS URL
    private final String DB_USER; // idk the db user and password
    private final String DB_PASSWORD;

    @Autowired
    public TransactionNotification(@Value("${FROM_EMAIL}") String FROM_EMAIL,
            @Value("${RDS_URL}") String RDS_URL,
            @Value("${DB_USER}") String DB_USER,
            @Value("${DB_PASSWORD}") String DB_PASSWORD) {
        this.FROM_EMAIL = FROM_EMAIL;
        this.RDS_URL = RDS_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Map<String, String> messageBody = parseMessage(msg.getBody());

            String transactionId = messageBody.get("transactionId");
            String transactionType = messageBody.get("transactionType");
            String details = messageBody.get("details");

            List<String> checkerRoles = getCheckerRoles(transactionType);
            List<String> userEmails = getUsersWithEmailsForRoles(checkerRoles);
            sendEmailNotifications(userEmails, transactionId, details);
        }
        return null;
    }

    private Map<String, String> parseMessage(String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, new TypeReference<HashMap<String,String>>() {});
        } catch (IOException e) {
            System.out.println("Error parsing message: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private List<String> getCheckerRoles(String transactionType) {
        Map<String, List<String>> roleMapping = new HashMap<>();
        roleMapping.put("Update points balance in Points Ledger", List.of("Owner"));
        roleMapping.put("Update user details in Users Storage", List.of("Manager", "Owner"));

        return roleMapping.getOrDefault(transactionType, new ArrayList<>());
    }

    private List<String> getUsersWithEmailsForRoles(List<String> roles) {
        List<String> emails = new ArrayList<>();
        String inSql = String.join(",", Collections.nCopies(roles.size(), "?"));

        try (Connection connection = DriverManager.getConnection(RDS_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT email FROM users WHERE role IN (" + inSql + ")";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int parameterIndex = 1;
                for (String role : roles) {
                    statement.setString(parameterIndex++, role);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        emails.add(resultSet.getString("email"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching emails: " + e.getMessage());
        }
        return emails;
    }

    private void sendEmailNotifications(List<String> userEmails, String transactionId, String details) {
        String subject = "Transaction Approval Needed";
        String body = String.format("Transaction %s created and pending approval. Details: %s", transactionId, details);

        for (String email : userEmails) {
            try {
                AmazonSimpleEmailService sesClient = AmazonSimpleEmailServiceClientBuilder.defaultClient();
                SendEmailRequest request = new SendEmailRequest()
                        .withSource(FROM_EMAIL)
                        .withDestination(new Destination().withToAddresses(email))
                        .withMessage(new Message()
                                .withBody(new Body().withText(new Content().withData(body)))
                                .withSubject(new Content().withData(subject)));

                sesClient.sendEmail(request);
            } catch (Exception e) {
                System.out.println("Error sending email: " + e.getMessage());
            }
        }
    }
}

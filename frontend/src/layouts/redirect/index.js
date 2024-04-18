// @mui material components
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDButton from "components/MDButton";
import MDTypography from "components/MDTypography";

// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import { useNavigate, useSearchParams } from "react-router-dom";
import { axiosPrivate } from "api/axios";

function Redirect() {
  const callback = useSearchParams()[0].get("callback")
  const navigate = useNavigate()

  function onClick() {
    axiosPrivate(callback).then(console.log)
    navigate("/users")
  }

  return (
    <DashboardLayout>
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <MDBox
              mx={2}
              mt={-3}
              py={3}
              px={2}
              variant="gradient"
              bgColor="info"
              borderRadius="lg"
              coloredShadow="info"
            >
              <MDTypography variant="h6" color="white">
                Accept transaction request
              </MDTypography>
            </MDBox>
            <MDBox py={3}>
              <MDTypography variant="h3">
                Are you sure you want to accept this request?
              </MDTypography>
            </MDBox>
            <MDButton variant="outlined" color="info" onClick={onClick}>
              Accept
            </MDButton>
          </Grid>
        </Grid>
      </MDBox>
    </DashboardLayout>
  );
}

export default Redirect;
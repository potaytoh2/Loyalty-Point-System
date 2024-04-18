import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Box, MenuItem, FormControl, InputLabel, Select, Card } from "@mui/material";
import MDBox from "components/MDBox";
import MDButton from "components/MDButton";
import MDTypography from "components/MDTypography";
import useAxiosPrivate from 'hooks/AxiosPrivate'
import UserPool from "../../UserPool";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";

const EnrollUser = () => {
  const [user, setUser] = useState({
    first_name: "",
    last_name: "",
    email: "",
    role: "",
  });
  const [error, setError] = useState("")

  const navigate = useNavigate();
  const handleBackClick = () => {
    navigate(-1);
  };

  const axiosPrivate = useAxiosPrivate(); // Use your custom hook here

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    let response
    UserPool.signUp(user.username, user.password, [{ Name: 'email', Value: user.email }], null, (err, data) => {
      if (err) {
        console.log("err", err)
        setError(err.message);
      } else {
        response = axiosPrivate.post('user/new_user', { ...user, id: data.userSub }).then(response =>
          navigate("/users")
        ).catch(err => {
          console.log("axios", err)
          setError(err.message)
        })
      }
    })
  };

  return (
    <DashboardLayout>
      <MDBox pt={6} pb={3}>
        <Card>
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
            <MDTypography variant="h5" color="white">
              Enroll New User
            </MDTypography>
          </MDBox>
          <MDBox p={3} display="flex" flexDirection="column" alignItems="center">
            <form style={{ width: "100%" }} onSubmit={handleSubmit}>
              <TextField
                fullWidth
                name="username"
                label="Username"
                value={user.username}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                name="password"
                label="Password"
                value={user.password}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                name="first_name"
                label="First Name"
                value={user.first_name}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                name="last_name"
                label="Last Name"
                value={user.last_name}
                onChange={handleChange}
                margin="normal"
              />
              <TextField
                fullWidth
                name="email"
                label="Email"
                value={user.email}
                onChange={handleChange}
                margin="normal"
              />
              <FormControl fullWidth margin="normal">
                <InputLabel>Role</InputLabel>
                <Select
                  name="role"
                  value={user.role}
                  onChange={handleChange}
                  label="Role"
                  sx={{
                    height: "3.4375em",
                    ".MuiOutlinedInput-notchedOutline": {
                      borderColor: "rgba(0, 0, 0, 0.23)",
                    },
                    ".MuiSelect-select": {
                      paddingTop: "10px",
                      paddingBottom: "10px",
                    },
                  }}
                >
                  <MenuItem value="Owner">Owner</MenuItem>
                  <MenuItem value="Manager">Manager</MenuItem>
                  <MenuItem value="Engineer">Engineer</MenuItem>
                  <MenuItem value="Product Manager">Product Manager</MenuItem>
                  <MenuItem value="">-</MenuItem>
                </Select>
              </FormControl>
              <MDBox display="flex" justifyContent="space-between" alignItems="center" width="100%" mt={8}>
                <MDButton onClick={handleBackClick} variant="contained" color="info">
                  Back
                </MDButton>
                <MDButton variant="outlined" color="info" type="submit">
                  Submit
                </MDButton>
              </MDBox>
              <MDTypography style={{ color: "red", fontSize: 20 }}>
                {error}
              </MDTypography>
            </form>
          </MDBox>
        </Card>
      </MDBox>
    </DashboardLayout>
  );
};

export default EnrollUser;

import { Stack } from "@mui/material";
import Container from "@mui/material/Container";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import React, { useEffect, useState } from "react";
import DoctorTable from "./DoctorTable";
import BasicTabs from "./TabsUI";
import RuleManagementTab from "./RuleManagementTab";

const OreoRulesScreen = () => {
  const [doctors, setDoctors] = useState([]);
  const [filteredDoctors, setFilteredDoctors] = useState([]);
  const [parameters, setParameters] = useState([]);

  useEffect(() => {
    fetch("api/doctors")
      .then((r) => {
        return r.json();
      })
      .then((data) => {
        setDoctors(data || []);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const tab1 = () => {
    return (
      <React.Fragment>
        <Container maxWidth="lg">
          <div style={{ display: "flex", justifyContent: "flex-start" }}>
            {" "}
            <h1>Doctors Data : </h1>
          </div>
          <div style={{ display: "flex", height: 600 }}>
            <DoctorTable doctors={doctors} />
          </div>
        </Container>
      </React.Fragment>
    );
  };

  const onSearch = () => {
    fetch(`api/doctors/search?${parameters}`)
      .then((r) => {
        if (r.ok) return r.json();
        else throw new Error(r.error);
      })
      .then((data) => {
        console.log(data);
        setFilteredDoctors(data || []);
      })
      .catch((err) => {
        setFilteredDoctors([]);
      });
  };
  const onTextChange = (event) => {
    setParameters(event.target.value);
  };

  const tab2 = () => {
    return <RuleManagementTab />;
  };

  const tab3 = () => {
    return (
      <React.Fragment>
        <h1>Rules Test</h1>
        <Stack spacing={8}>
          <TextField
            variant="standard"
            label="Search Parameters"
            value={parameters}
            onChange={onTextChange}
          />
          <Button variant="contained" onClick={onSearch}>
            Search
          </Button>
          <Box sx={{ width: "100%", height: 600 }}>
            <DoctorTable doctors={filteredDoctors} />
          </Box>
        </Stack>
      </React.Fragment>
    );
  };

  return (
    <React.Fragment>
      <h1>Oreo Rules Demo</h1>
      <Container maxWidth="lg">
        <BasicTabs
          tab1Name="Doctors Data Repo"
          tab2Name="Rules Management"
          tab3Name="Test Rules "
          tab1={tab1}
          tab2={tab2}
          tab3={tab3}
        />
      </Container>
    </React.Fragment>
  );
};

export default OreoRulesScreen;

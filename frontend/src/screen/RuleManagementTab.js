import React, { useState, useCallback } from "react";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import Input from "@mui/material/Input";

const RuleManagementTab = () => {
  const [uploadFile, setUploadFile] = useState(null);

  const handlechange = useCallback((event) => {
    setUploadFile(event.target.files[0]);
  }, []);
  const handleUpload = useCallback(
    (event) => {
      const formData = new FormData();
      console.log(uploadFile);
      formData.append("file", uploadFile);
      fetch("api/doctors/ruleUpload", {
        method: "POST",
        body: formData,
      })
        .then((res) => {
          if (res.ok) {
            alert("Uploaded successful! ");
          } else {
            throw new Error(res.error);
          }
        })
        .catch((err) => {
          alert(
            "uploaded failed. don't ask me why 0_-. Let check it in console log"
          );
          console.log(err);
        });
    },
    [uploadFile]
  );

  const handleDownload = useCallback((event) => {
    fetch("api/doctors/ruleDownload")
      .then((res) => {
        return res.blob();
      })
      .then((data) => {
        var a = document.createElement("a");
        a.href = window.URL.createObjectURL(data);
        a.download = "decisionTable.xls";
        a.click();
      });
  }, []);

  return (
    <React.Fragment>
      <h1> Rule Managements</h1>
      <Stack direction="row" spacing={4} justifyContent="center">
        <Input type="file" onChange={handlechange} />
        <Button variant="contained" onClick={handleUpload}>
          Upload
        </Button>
        <Button variant="contained" onClick={handleDownload}>
          Download
        </Button>
      </Stack>
    </React.Fragment>
  );
};

export default RuleManagementTab;

import React from "react";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";

const DoctorTable = ({ doctors }) => {
  const renderData = (doctors) => {
    return {
      rows: doctors.map((d) => {
        return {
          ...d,
          specialtyName: d.specialty.name,
          hospitalName: d.hospital.name,
        };
      }),
      columns: [
        { field: "id", headerName: "ID", width: 20 },
        { field: "doctorName", headerName: "NAME", width: 150 },
        { field: "gender", headerName: "GENDER", width: 150 },
        { field: "insurerPanel", headerName: "INSURER PANEL", width: 150 },
        { field: "specialtyName", headerName: "SPECIALTY NAME", width: 150 },
        { field: "graduatedDate", headerName: "Graduated Date", width: 150 },
        { field: "hospitalName", headerName: "HOSPITAL NAME", width: 150 },
      ],
    };
  };
  return (
    <React.Fragment>
      <DataGrid
        {...renderData(doctors)}
        components={{ Toolbar: GridToolbar }}
      />
    </React.Fragment>
  );
};

export default DoctorTable;

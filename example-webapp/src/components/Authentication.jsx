import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import {getToken, setToken} from "../services/localStorageService";
import { Box, CircularProgress, Typography } from "@mui/material";
import axios from "axios";

export default function Authentication() {
    const navigate = useNavigate();
    const [isLoggedin, setIsLoggedin] = useState(false);

    const getData = async (accessToken) => {
        await axios.post(`http://localhost:8080/api/auth/outbound/authentication?code=${accessToken}`)
            .then(res => {
                setToken(res.data.result.token);
                setIsLoggedin(true);
            }).catch( error => {
                console.log(error);
            })
    }

    useEffect(() => {
        const accessTokenRegex = /code=([^&]+)/;
        const isMatch = window.location.href.match(accessTokenRegex);

        if (isMatch) {
            const accessToken = isMatch[1];
            getData(accessToken).then((re) => {});
        }
    }, []);

    useEffect(() => {
        if (isLoggedin) {
            navigate("/");
        }
    }, [isLoggedin, navigate]);

    return (
        <>
            <Box
                sx={{
                    display: "flex",
                    flexDirection : "column",
                    gap: "30px",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100vh",
                }}
            >
                <CircularProgress></CircularProgress>
                <Typography>Authenticating...</Typography>
            </Box>
        </>
    );
}
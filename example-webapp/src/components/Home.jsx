import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {getToken} from "../services/localStorageService";
import Header from "./header/Header";
import {Avatar, Box, Card, CircularProgress, Typography} from "@mui/material";
import "../App.css"
import axios from "axios";

export default function Home() {
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState({});

    const getUserDetails = async (accessToken) => {
        const response = await axios.get(
            `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${accessToken}`
        ).then( res => {
            setUserDetails(res.data);
            }
        ).catch( error => {
            console.error(error)
        });
    };

    useEffect(() => {
        const accessToken = getToken();

        if (!accessToken) {
            navigate("/login");
        } else {
            getUserDetails(accessToken).then(r => {});
        }
    }, [navigate]);

    return (
        <>
            <Header></Header>
            {userDetails ? (
                <Box
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    justifyContent="center"
                    height="100vh"
                    bgcolor={"#f0f2f5"}
                >
                    <Card
                        sx={{
                            minWidth: 350,
                            maxWidth: 500,
                            boxShadow: 4,
                            borderRadius: 4,
                            padding: 4,
                        }}
                    >
                        <Box
                            sx={{
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "center",
                                width: "100%",
                                gap: "10px",
                            }}
                        >
                            <Typography
                                sx={{
                                    fontSize: 18,
                                    mb: "40px",
                                }}
                            >
                                Welcome, {userDetails.name} !
                            </Typography>
                                <Avatar
                                    src={userDetails.picture}
                                    alt={`${userDetails.given_name}'s profile`}
                                    sx={{ width: 100, height: 100}}
                                />
                            <Box
                                sx={{
                                    display: "flex",
                                    flexDirection: "row",
                                    justifyContent: "space-between",
                                    alignItems: "flex-start",
                                    width: "100%", // Ensure content takes full width
                                }}
                            >
                                <Typography
                                    sx={{
                                        fontSize: 14,
                                        fontWeight: 600,
                                    }}
                                >
                                    Email
                                </Typography>
                                <Typography
                                    sx={{
                                        fontSize: 14,
                                    }}
                                >
                                    {userDetails.email}
                                </Typography>
                            </Box>
                            <Box
                                sx={{
                                    display: "flex",
                                    flexDirection: "row",
                                    justifyContent: "space-between",
                                    alignItems: "flex-start",
                                    width: "100%", // Ensure content takes full width
                                }}
                            >
                                <Typography
                                    sx={{
                                        fontSize: 14,
                                        fontWeight: 600,
                                    }}
                                >
                                    Name
                                </Typography>
                                <Typography
                                    sx={{
                                        fontSize: 14,
                                    }}
                                >
                                    {userDetails.name}
                                </Typography>
                            </Box>

                        </Box>
                    </Card>
                </Box>
            ) : (
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        gap: "30px",
                        justifyContent: "center",
                        alignItems: "center",
                        height: "100vh",
                    }}
                >
                    <CircularProgress></CircularProgress>
                    <Typography>Loading ...</Typography>
                </Box>
            )}
        </>
    );
}

import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {getToken, setToken} from "../services/localStorageService";
import {OAuthConfig} from "../configurations/configuration";

export default function Login() {
    const navigate = useNavigate();

    const handleCloseSnackBar = (event, reason) => {
        if (reason === "clickaway") {
            return;
        }

        setSnackBarOpen(false);
    };

    const handleClick = () => {
        alert(
            "Please refer to Oauth2 series for this implemetation guidelines. https://www.youtube.com/playlist?list=PL2xsxmVse9IbweCh6QKqZhousfEWabSeq"
        );
    };

    useEffect(() => {
        const accessToken = getToken();

        if (accessToken) {
            navigate("/");
        }
    }, [navigate]);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarMessage, setSnackBarMessage] = useState("");

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json", // Set the content type to JSON
            },
            body: JSON.stringify({
                userName: username,
                password: password,
            }),
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                console.log("Response body:", data);

                // This code is a commitment between BE and FE

                setToken(data.result?.token);

                console.log(getToken());

                navigate("/");
            })
            .catch((error) => {
                setSnackBarMessage(error.message);
                setSnackBarOpen(true);
            });
    };

    const handleGoogleAuthSubmit = async () => {
        const callbackUrl = OAuthConfig.redirectUri;
        const authUrl = OAuthConfig.authUri;
        const googleClientId = OAuthConfig.clientId;

        const  targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
            callbackUrl
        )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

        console.log("TargetURL: -> ",targetUrl);

        window.location.href = targetUrl;
    }

    return (
        <div className="login-container">
            <div className="login-form">
                <form>
                    <div className={text-align:"center"}>
                        <h2>Login</h2>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputEmail1" className="form-label">Email address</label>
                        <input type="email" className="form-control" id="exampleInputEmail1"
                               aria-describedby="emailHelp"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputPassword1" className="form-label">Password</label>
                        <input type="password" className="form-control" id="exampleInputPassword1"/>
                    </div>
                    <div className="mb-3 form-check">
                        <input type="checkbox" className="form-check-input" id="exampleCheck1"/>
                        <label className="form-check-label" htmlFor="exampleCheck1">Check me out</label>
                    </div>
                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
        /*<>
            <Snackbar
                open={snackBarOpen}
                onClose={handleCloseSnackBar}
                autoHideDuration={6000}
                anchorOrigin={{vertical: "top", horizontal: "right"}}
            >
                <Alert
                    onClose={handleCloseSnackBar}
                    severity="error"
                    variant="filled"
                    sx={{width: "100%"}}
                >
                    {snackBarMessage}
                </Alert>
            </Snackbar>
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
                        minWidth: 400,
                        maxWidth: 500,
                        boxShadow: 4,
                        borderRadius: 4,
                        padding: 4,
                    }}
                >
                    <CardContent>
                        <Typography variant="h5" component="h1" gutterBottom>
                            Welcome
                        </Typography>
                        <Box
                            component="form"
                            display="flex"
                            flexDirection="column"
                            alignItems="center"
                            justifyContent="center"
                            width="100%"
                            onSubmit={handleSubmit}
                        >
                            <TextField
                                label="Username"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                            <TextField
                                label="Password"
                                type="password"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                size="large"
                                onClick={handleSubmit}
                                fullWidth
                                sx={{
                                    mt: "15px",
                                    mb: "25px",
                                }}
                            >
                                Login
                            </Button>
                            <Divider></Divider>
                        </Box>

                        <Box display="flex" flexDirection="column" width="100%" gap="25px">
                            <Button
                                type="button"
                                variant="contained"
                                color="secondary"
                                size="large"
                                onClick={handleGoogleAuthSubmit}
                                fullWidth
                                sx={{gap: "10px"}}
                            >
                                <GoogleIcon/>
                                Continue with Google
                            </Button>
                            <Button
                                type="submit"
                                variant="contained"
                                color="success"
                                size="large"
                            >
                                Create an account
                            </Button>
                        </Box>
                    </CardContent>
                </Card>
            </Box>
        </>*/
    );
}

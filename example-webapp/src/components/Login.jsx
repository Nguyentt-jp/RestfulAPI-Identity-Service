import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {getToken, setToken} from "../services/localStorageService";
import {OAuthConfig} from "../configurations/configuration";
import { FaGoogle } from "react-icons/fa6";
import { FaFacebookF } from "react-icons/fa6";

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

            });
    };

    const handleGoogleAuthSubmit = async () => {
        const callbackUrl = OAuthConfig.redirectUri;
        const authUrl = OAuthConfig.authUri;
        const googleClientId = OAuthConfig.clientId;

        const targetUrl = `${authUrl}?redirect_uri=${encodeURIComponent(
            callbackUrl
        )}&response_type=code&client_id=${googleClientId}&scope=openid%20email%20profile`;

        console.log("TargetURL: -> ", targetUrl);

        window.location.href = targetUrl;
    }

    return (
        <div className="vh-100">
            <div className="container py-5 h-100">
                <div className="row d-flex justify-content-center align-items-center h-100">
                    <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div className="card shadow-2-strong" style={{borderRadius: "1rem"}}>
                            <div className="card-body p-5 text-center">

                                <h1 className="mb-5">Login in</h1>

                                <div className="form-outline mb-4">
                                    <input type="email"
                                           id="typeEmailX-2"
                                           className="form-control form-control-lg"
                                           placeholder="Username"
                                    />
                                </div>

                                <div className="form-outline mb-4">
                                    <input type="password"
                                           id="typePasswordX-2"
                                           className="form-control form-control-lg"
                                           placeholder="Password"
                                    />
                                </div>

                                <div className="form-check d-flex justify-content-start mb-4">
                                    <input className="form-check-input" type="checkbox" value="" id="form1Example3"/>
                                    <label className="form-check-label" > Remember password </label>
                                </div>

                                <button className="btn btn-primary btn-lg btn-block"
                                        style={{
                                            width: "320px",
                                        }}
                                        type="submit"
                                        onClick={handleSubmit}
                                >
                                    Login
                                </button>

                                <hr className="my-4"/>

                                <div style={{
                                    display: "flex",
                                    flexDirection: "column",
                                    alignItems: "center",
                                }}>
                                    <button className="btn btn-lg btn-block btn-primary"
                                            style={{
                                                backgroundColor: "#dd4b39",
                                                width: "320px",
                                                marginBottom: "10px",
                                            }}
                                            type="submit"
                                            onClick={handleGoogleAuthSubmit}
                                    >
                                        <FaGoogle className="me-2"/> Sign in with google
                                    </button>
                                    <button className="btn btn-lg btn-block btn-primary mb-2"
                                            style={{
                                                backgroundColor: "#3b5998",
                                                width: "320px",
                                            }}
                                            type="submit"
                                    >
                                        <FaFacebookF
                                            className="me-2"
                                            style={{

                                            }}
                                        />Sign in with facebook
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        /*
            <>
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

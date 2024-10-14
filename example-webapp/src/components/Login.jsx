import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {getToken, setToken} from "../services/localStorageService";
import {OAuthConfig} from "../configurations/configuration";
import { FaGoogle } from "react-icons/fa6";
import { FaFacebookF } from "react-icons/fa6";
import axios from "axios";

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

    const handleSignup = (event) => {

    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await axios.post("http://localhost:8080/api/auth/login", {
            userName: username,
            password: password,
        },
        {
            headers: {
                "Content-Type": "application/json", // Set the content type to JSON
            }
        }).then((response) => {
            console.log(response.data);
            setToken(response.data.result?.token);
            navigate("/");
        }).catch(
            (error) => console.log(error)
        );
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
                                           onChange={(event) => {setUsername(event.target.value)}}
                                    />
                                </div>

                                <div className="form-outline mb-4">
                                    <input type="password"
                                           id="typePasswordX-2"
                                           className="form-control form-control-lg"
                                           placeholder="Password"
                                           onChange={(event) => {setPassword(event.target.value)}}
                                    />
                                </div>

                                <div className="form-check d-flex justify-content-start mb-4">
                                    <input className="form-check-input" type="checkbox" value="" id="form1Example3"/>
                                    <label className="form-check-label"> Remember password </label>
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
                                <button className="btn btn-primary btn-lg btn-block"
                                        style={{
                                            marginTop: "5px",
                                            width: "320px",
                                        }}
                                        type="submit"
                                        onClick={handleSignup}
                                >
                                    Signup
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
                                            style={{}}
                                        />Sign in with facebook
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {getToken} from "../services/localStorageService";
import Header from "./header/Header";
import "../App.css"
import axios from "axios";
import {OrbitProgress} from "react-loading-indicators";

export default function Home() {
    const navigate = useNavigate();
    const [userDetails, setUserDetails] = useState({});

    const getUserDetails = async (accessToken) => {
        const response = await axios.get(
            `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${accessToken}`
        ).then( res => {
            setUserDetails(res.data);
            console.log(res.data);
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
    }, []);

    return (
        <>
            <Header></Header>
            {userDetails ? (
                <div className="vh-100">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card shadow-2-strong" style={{borderRadius: "1rem"}}>
                                    <div className="card-body p-5">

                                        <div className="text-center">
                                            <h3 className="mb-5">Welcome {userDetails.name}</h3>
                                            <img
                                                src={userDetails.picture}
                                                alt=""
                                                style={{
                                                    borderRadius: "100px",
                                                    height: "100px",
                                                    width: "100px",
                                                }}
                                            />
                                        </div>

                                        <div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>UserId:</label>
                                                <label>{userDetails.id}</label>
                                            </div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>Username:</label>
                                                <label>{userDetails.email}</label>
                                            </div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>FirstName:</label>
                                                <label>{userDetails.given_name}</label>
                                            </div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>Username:</label>
                                                <label>{userDetails.family_name}</label>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            ) : (
                <div className="d-flex justify-content-center align-items-center"
                     style={{height: '100vh'}}
                >
                    <OrbitProgress color="#52edf4" size="large" text="Loading..." textColor="#000000"/>
                </div>
            )}
        </>
    );
}

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
    const [password, setPassword] = useState("");
    const [viewInputPassword, setViewInputPassword] = useState(false);

    const handleSavePassword = async () => {
        const accessToken = getToken();
        await axios.post(
            `http://localhost:8080/api/users/create-password`,{
                password: password,
            },
            {
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${accessToken}`
                }
            }
        ).then(
            (response) => {
                console.log("Response Data: ",response.data?.result);
                setViewInputPassword(!userDetails.noPassword);
            }
        ).catch((error) => {
            console.log(error);
        });
    }

    const getUserDetails = async (accessToken) => {
        const response = await axios.get(
            //`https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${accessToken}`
            `http://localhost:8080/api/users/myinfo`,{
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                }
            }
        ).then( res => {
            setUserDetails(res.data?.result);
            setViewInputPassword(res.data?.result.noPassword);
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
                                            <h3 className="mb-5">Welcome {userDetails.email}</h3>
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
                                                <label>{userDetails.userId}</label>
                                            </div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>Username:</label>
                                                <label>{userDetails.userName}</label>
                                            </div>
                                            <div>
                                                <label className="form-label"
                                                       style={{width: "108px"}}>FirstName:</label>
                                                <label>{userDetails.lastName}</label>
                                            </div>
                                            <div>
                                                <label className="form-label" style={{width: "108px"}}>Username:</label>
                                                <label>{userDetails.firstName}</label>
                                            </div>
                                            {viewInputPassword ? (
                                                <>
                                                    <div className="form-outline mb-4">
                                                        <label className="form-label">Create Password</label>
                                                        <input type="password"
                                                               className="form-control form-control-lg"
                                                               placeholder="Password"
                                                               onChange={(e) => setPassword(e.target.value)}
                                                        />
                                                    </div>
                                                    <div className="d-flex justify-content-center align-items-center">
                                                        <button className="btn btn-primary btn-lg btn-block"
                                                                style={{
                                                                    width: "fit-content",
                                                                }}
                                                                type="submit"
                                                                onClick={handleSavePassword}
                                                        >
                                                            Save Password
                                                        </button>
                                                    </div>
                                                </>
                                            ) : (
                                                <></>
                                            )}
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

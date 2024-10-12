import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import {setToken} from "../services/localStorageService";
import axios from "axios";
import {OrbitProgress} from "react-loading-indicators";

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
        <div className="d-flex justify-content-center align-items-center"
            style={{height:'100vh'}}
        >
            <OrbitProgress color="#52edf4" size="large" text="Loading..." textColor="#000000"/>
        </div>
    );
}
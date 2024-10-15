import {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import {Col, Form, Row} from "react-bootstrap";
import axios from "axios";
import {getToken} from "../services/localStorageService";

export default function CreateUserModals() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");

    const handleSignup = async () => {
        const accessToken = getToken();
        await axios.post(
            `http://localhost:8080/api/users`,{
                userName: username,
                password: password,
                firstName: firstName,
                lastName: lastName,
                email: email,
            },{
                headers:{
                    "Content-Type": "application/json",
                }
            }
        ).then( res => {
            console.log(res.data?.result);
        }).catch(error => {
            console.log(error)
        });

        handleClose();
    }

    return (
        <>
            <Button variant=" btn btn-primary btn-lg" onClick={handleShow} style={{
                width: "320px",
                marginTop: "5px",
            }}
            >
                SignUp
            </Button>

            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
            >
                <Modal.Header closeButton>
                    <Modal.Title>Add User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridEmail">
                                <Form.Label>FirstName</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="FirstName"
                                    value={firstName}
                                    onChange={(event) => {setFirstName(event.target.value)}}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridPassword">
                                <Form.Label>LastName</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="LastName"
                                    value={lastName}
                                    onChange={(event) => {setLastName(event.target.value)}}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridEmail">
                                <Form.Label>UserName</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter Username"
                                    value={username}
                                    onChange={(event) => {setUsername(event.target.value)}}
                                />
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridPassword">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Password"
                                    value={password}
                                    onChange={(event) => {setPassword(event.target.value)}}
                                />
                            </Form.Group>
                        </Row>

                        <Form.Group className="mb-3" controlId="formGridAddress1">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                type="mail"
                                placeholder="Email"
                                value={email}
                                onChange={(event) => {setEmail(event.target.value)}}
                            />
                        </Form.Group>

                        {/*<Row className="mb-3">
                            <Form.Group as={Col} controlId="formGridPicture">
                                <input className="form-control" type="file"/>
                            </Form.Group>
                        </Row>

                        <Form.Group className="mb-3" id="formGridCheckbox">
                            <Form.Check type="checkbox" label="Remember Login" />
                        </Form.Group>*/}
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleSignup}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}
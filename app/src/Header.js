import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { UserContext } from './UserContext';

class Header extends React.Component {
    state = {
        show: false
    }

    handleClose = () => {
        this.setState({ show: false });
    }

    handleShow = () => {
        this.setState({ show: true });
    }

    handleLogin = () => {
        //TODO: call login routine on backend and decide which user to log in and if it succeeds
        this.context.loginUser('Lukas', 1);
        this.setState({ show: false});
    }

    handleLogout = () => {
        this.context.logoutUser();
    }


    render() {
        console.log(this.context);
        return (
            <UserContext.Consumer>
                {(user) => (
                    <>
                    <Navbar collapseOnSelect expand="md" bg="dark" variant="dark" sticky="top">
                        <Navbar.Brand as={Link} to="/">GPS Race</Navbar.Brand>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                        <Navbar.Collapse id="responsive-navbar-nav">
                            <Nav className="mr-auto">
                                <Nav.Link as={Link} to="/" href="/">Home</Nav.Link>
                                <Nav.Link as={Link} to="/viewer" href="/viewer">View Races</Nav.Link>
                                {this.context.userId && (<Nav.Link as={Link} to="/participant" href="participant">Participant</Nav.Link>)}
                            </Nav>
                            <Nav>
                                {!user.userId ? (
                                    <Nav.Link variant="dark" onClick={this.handleShow}>Login</Nav.Link>
                                ) : (
                                <Nav.Link variant="dark" onClick={this.handleLogout}>Logout {user.username}</Nav.Link>
                                )}
                            </Nav>
                        </Navbar.Collapse>
                    </Navbar>
                    <Modal size="sm" show={this.state.show} id="loginModal" onHide={this.handleClose}>
                        <Modal.Header closeButton>
                            <Modal.Title>Login</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form>
                                <Form.Group controlId="formUsername">
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control placeholder="Enter username"></Form.Control>
                                </Form.Group>
                                <Form.Group controlId="formPassword">
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control type="password" placeholder="Enter password"></Form.Control>
                                </Form.Group>
                            </Form>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>Cancel</Button>
                            <Button variant="primary" onClick={this.handleLogin}>Login</Button>
                        </Modal.Footer>
                    </Modal>
                    </>
                )
            }
            </UserContext.Consumer>
        );
    }
}

Header.contextType = UserContext;

export default Header;
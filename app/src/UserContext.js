import React from 'react';

export const UserContext = React.createContext(
    {
        username: undefined,
        userId: undefined,
        loginUser: (username, userId) => {},
        logoutUser: () => {}
    }
);
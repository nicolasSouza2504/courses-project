import React, {FC, useEffect, useState} from 'react';
import {Store} from "react-notifications-component";

const NotificationComponent = {

    triggerNotification: (type: 'success' | 'danger' | 'info' | 'default' | 'warning', message: string, title: string) => {

        Store.addNotification({
            title: title,
            message: message,
            type: type,
            insert: "top",
            container: "top-center",
            animationIn: ["animated", "fadeIn"],
            animationOut: ["animated", "fadeOut"],
            dismiss: {
                duration: 2000
            },
            content: (
                <div style={NotificationComponent.getNotificationStyle(type)}>
                    <strong>{title}</strong> - {message}
                </div>
            )
        });

    },

    getNotificationStyle: (type: string): object => {

        switch (type) {
            case 'success':
                return { backgroundColor: '#28a745', color: '#fff', padding: '10px', borderRadius: '5px' };
            case 'danger':
                return { backgroundColor: '#dc3545', color: '#fff', padding: '10px', borderRadius: '5px'  };
            case 'info':
                return { backgroundColor: '#17a2b8', color: '#fff', padding: '10px', borderRadius: '5px'  };
            default:
                return {};
        }

    }

};

export default NotificationComponent;

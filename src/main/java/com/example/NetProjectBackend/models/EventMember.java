package com.example.NetProjectBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMember {
    private int id;
    private int userId;
    private int eventId;
    private String status;
    private boolean is_owner;
}

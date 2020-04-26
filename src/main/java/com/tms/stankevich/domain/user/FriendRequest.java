package com.tms.stankevich.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"user_request", "user_response"})})
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_request", nullable=false)
    private User userRequest;

    @ManyToOne
    @JoinColumn(name="user_response", nullable=false)
    private User userResponse;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private FriendRequestStatus status;


}

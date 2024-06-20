package com.example.grpc;

import io.grpc.stub.StreamObserver;
import user.UserServiceGrpc;
import user.UserServiceOuterClass.CreateUserRequest;
import user.UserServiceOuterClass.CreateUserResponse;
import user.UserServiceOuterClass.GetUserRequest;
import user.UserServiceOuterClass.GetUserResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    //Declare Attributes
    private Map<String, GetUserResponse> users = new HashMap<>();

    //Implement method
    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        GetUserResponse response = users.getOrDefault(request.getUserId(), GetUserResponse.newBuilder().build());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        String userId = UUID.randomUUID().toString();
        GetUserResponse user = GetUserResponse.newBuilder()
                .setUserId(userId)
                .setName(request.getName())
                .setAge(request.getAge())
                .build();
        users.put(userId, user);

        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setUserId(userId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
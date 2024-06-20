package com.example.grpc;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private Map<String, GetUserResponse> users = new HashMap<>();

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
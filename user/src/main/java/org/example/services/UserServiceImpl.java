package org.example.services;

import com.grpc.IsUserBlackList;
import com.grpc.User;
import com.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.TemporaryDB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl{
    @GrpcClient("grpc-server")
    UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("grpc-server")
    UserServiceGrpc.UserServiceStub userServiceStub;
    public boolean isUserBlackList(String id){
        try{
            User user = User.newBuilder().setId(id).setName("Phuong").build();
            IsUserBlackList isUserBlackList = userServiceBlockingStub.isBlackList(user);
            return isUserBlackList.getIsBlacklist();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void getUserStream(){
        List<User> users = new ArrayList<>();
        userServiceStub.getUserStream(User.newBuilder().setId("1").build(), new StreamObserver<User>() {
            @Override
            public void onNext(User user) {
                users.add(user);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }
        });
        users.forEach(user -> System.out.println(user.getName()));
    }

    public void sendUserStream(){
        List<User> users = TemporaryDB.users;
        StreamObserver<User> responseObserver = userServiceStub.sendUserStream(new StreamObserver<User>() {
            User wantedUser;
            @Override
            public void onNext(User user) {
                wantedUser = user;
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println(wantedUser.getName());
            }
        });

        users.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}

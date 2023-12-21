package org.example.services;
import com.grpc.IsUserBlackList;
import com.grpc.User;
import com.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.TemporaryDB;
import java.util.List;

@GrpcService
public class UserServerService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void isBlackList(User request, StreamObserver<IsUserBlackList> responseObserver) {
        responseObserver.onNext(IsUserBlackList.newBuilder().setIsBlacklist(false).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getUserStream(User request, StreamObserver<User> responseObserver) {
        List<User> users = TemporaryDB.users;
        users.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<User> sendUserStream(StreamObserver<User> responseObserver) {
        return new StreamObserver<User>() {
            User wantedUser;
            @Override
            public void onNext(User user) {
                if(user.getId().equals("User2")){
                    wantedUser = user;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error");
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(wantedUser);
                responseObserver.onCompleted();
            }
        };
    }
}

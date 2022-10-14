package top.javatool.canal.example.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.haoyu.common.canal.client.annotation.CanalTable;
import com.haoyu.common.canal.client.handler.EntryHandler;
import top.javatool.canal.example.model.User;


@Component
@CanalTable(value = "t_user")
public class UserHandler implements EntryHandler<User> {


    private Logger logger = LoggerFactory.getLogger(UserHandler.class);


    @Override
    public void insert(User user) {
        logger.info("insert message  {}", user);
    }

    @Override
    public void update(User before, User after) {
        logger.info("update before {} ", before);
        logger.info("update after {}", after);
    }

    @Override
    public void delete(User user) {
        logger.info("delete  {}", user);
    }
}

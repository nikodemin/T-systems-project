import com.t_systems.webstore.config.WebConfig;
import com.t_systems.webstore.entity.User;
import com.t_systems.webstore.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class DbTests
{
    @Autowired
    private UserService userService;

    @Test
    public void addUserTest()
    {
        User user = new User();
        user.setEmail("niko@gmail.com");
        user.setUsername("niko");
        userService.addUser(user);
        System.out.println("USER="+user);

        user = new User();
        user.setEmail("bob@gmail.com");
        user.setUsername("bob");
        userService.addUser(user);
        System.out.println("USER="+user);

        user = new User();
        user.setEmail("emily@gmail.com");
        user.setUsername("emily");
        userService.addUser(user);
        System.out.println("USER="+user);
    }

    @Test
    public void usersSize()
    {
        Assert.assertEquals(3,userService.getAllUsers().size());
    }

    @Test
    public void dublicateTest()
    {
        User user = new User();
        user.setEmail("niko@gmail.com");
        user.setUsername("niko");
        userService.addUser(user);
        if (userService.addUser(user) !=null)
            Assert.fail();
    }
}

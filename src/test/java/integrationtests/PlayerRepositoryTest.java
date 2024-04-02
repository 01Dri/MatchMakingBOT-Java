package integrationtests;

import me.dri.core.entities.Player;
import me.dri.core.enums.QueueStatus;
import me.dri.core.enums.Rank;
import me.dri.core.repositories.PlayerRepository;
import me.dri.core.repositories.PlayerRepositoryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerRepositoryTest {

    private PlayerRepository playerRepository;
    private  SessionFactory sessionFactory;

    @Before
    public void setup() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // ou qualquer outra forma de configuração
        this.sessionFactory = configuration.buildSessionFactory();
        this.playerRepository = new PlayerRepositoryImpl(sessionFactory);
    }

    @Test
    public void testSavePlayer() {
        Player player = new Player(null, "teste", "drikill", Rank.RANK_A, 120, 10, 20, QueueStatus.DEFAULT);
        this.playerRepository.save(player);
    }

    @Test
    public void testFindPlayerByDiscordName() {
        var result = this.playerRepository.findPlayerByDiscordName("drikill.");
        Assert.assertNotNull(result);
    }


}

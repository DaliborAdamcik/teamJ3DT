package sk.tsystems.forum.junittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sk.tsystems.forum.junittest.entity.BASEEntityTest;
import sk.tsystems.forum.junittest.entity.CommentEntityTest;
import sk.tsystems.forum.junittest.entity.TopicEntityTest;
import sk.tsystems.forum.junittest.entity.UserEntityTest;
import sk.tsystems.forum.junittest.servicesJPA.CommentJPATest;
import sk.tsystems.forum.junittest.servicesJPA.TopicJPATest;
import sk.tsystems.forum.junittest.servicesJPA.UserJPATest;

@RunWith(Suite.class)
@SuiteClasses({ TESTtestHelper.class, 
	BASEEntityTest.class, CommentEntityTest.class, TopicEntityTest.class, UserEntityTest.class,
	CommentJPATest.class, TopicJPATest.class, UserJPATest.class })

public class AllTestsRun {

}

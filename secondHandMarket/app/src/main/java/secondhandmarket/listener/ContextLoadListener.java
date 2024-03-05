package secondhandmarket.listener;

import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("웹애플리케이션 자원 준비!");

        // DB 커넥션, DAO, 트랜잭션 관리자 생성
        DBConnectionPool connectionPool = new DBConnectionPool(
                "jdbc:mysql://db-ld296-kr.vpc-pub-cdb.ntruss.com/private", "study", "Bitcamp!@#123");
        TransactionManager txManager = new TransactionManager(connectionPool);
        UserDaoImpl userDao = new UserDaoImpl(connectionPool);
        GoodsDaoImpl goodsDao = new GoodsDaoImpl(connectionPool);
        UserPhotoDaoImpl userPhotoDao = new UserPhotoDaoImpl(connectionPool);
        GoodsPhotoDaoImpl goodsPhotoDao = new GoodsPhotoDaoImpl(connectionPool);


        ServletContext context = sce.getServletContext();
        context.setAttribute("txManager", txManager);
        context.setAttribute("userDao", userDao);
        context.setAttribute("goodsDao", goodsDao);
        context.setAttribute("userPhotoDao", userPhotoDao);
        context.setAttribute("goodsPhotoDao", goodsPhotoDao);




    }
}

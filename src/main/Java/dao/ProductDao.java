package dao;

import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public void addProduct(Product p) throws SQLException {
        String sql="INSERT INTO products(id,name,price,category,pnum,imgurl,description) values(?,?,?,?,?,?,?)";
        QueryRunner queryRunner= new QueryRunner(DataSourceUtils.getDataSource());
        int row=queryRunner.update(sql,p.getId(),p.getName(),p.getPrice(),p.getCategory(),p.getPnum(),p.getImgurl(),p.getDescription());
        if (row==0){
            throw new RuntimeException();

        }



//        private String id;
//        private String name;
//        private double price;
//        private String category;
//        private int pnum;
//        private String imgurl;
//        private String description;
    }

//     销售榜单
    public List<Object[]> salesList(String year, String month)
            throws SQLException {
        String sql = "SELECT products.name,SUM(orderitem.buynum) AS totalsalnum " +
                "FROM orders,products,orderItem " +
                "WHERE orders.id=orderItem.order_id AND products.id=orderitem.product_id AND orders.paystate=1 AND year(ordertime)=? and month(ordertime)=? " +
                "GROUP BY products.name ORDER BY totalsalnum DESC";
//        can use INNER JOIN as below
//        String sql="SELECT name,SUM(buynum) AS totalsalnum FROM orders INNER JOIN orderitem ON orders.id=orderitem.order_id INNER JOIN products on products.id=orderitem.product_id GROUP BY name ORDER BY totalsalnum DESC;"
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql, new ArrayListHandler(), year, month);
    }


    public List<Product> listAll() throws SQLException {
        String sql="SELECT * FROM products";
        QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class));

    }

    public Product findProductById(String id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id=?";
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<Product>(Product.class),id);
    }



    public void deleteProduct(String id) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        int num = queryRunner.update(sql, id);
        if (num <= 0) {
            throw new RuntimeException();
        }


    }

    public static void main(String[] args) throws SQLException {
//       Product product=new Product();
       ProductDao productDao=new ProductDao();
//       productDao.addProduct(product);
        List<Product> list=
        productDao.findByPage(1,4,"文学");
        if (list==null){
            System.out.println("null");
        }
    }

    public int findAllCount(String category) throws SQLException {
        String sql="SELECT count(*) FROM products";
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

        if (!"全部商品".equals(category)) {
            // ATTENTION !! the space(before 'WHERE') is necessary !
            sql+=" WHERE category=?";
            return ((Long) queryRunner.query(sql, new ScalarHandler(), category)).intValue();

        }else {
            return  ((Long) queryRunner.query(sql,new ScalarHandler())).intValue();
        }
    }

    public void editProduct(Product p) throws SQLException {
        String sql = "UPDATE products set name=?,price=?,category=?,pnum=?,imgurl=?,description=? WHERE id=?";
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        int num = queryRunner.update(sql, p.getId());
        if (num <= 0) {
            throw new RuntimeException();
        }
    }

    public List<Product> findProductByManyCondition(String id, String name, String category, String minprice, String maxprice) throws SQLException{
        List<Object> list = new ArrayList<Object>();
        String sql = "select * from products where 1=1 ";
        // 1=1 is to make this sentence right(can be run alone)....

        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

        if (id != null && id.trim().length() > 0) {
            sql += " and id=?";
            list.add(id);
        }

        if (name != null && name.trim().length() > 0) {
            sql += " and name=?";
            list.add(name);
        }
        if (category != null && category.trim().length() > 0) {
            sql += " and category=?";
            list.add(category);
        }
        if (minprice != null && maxprice != null
                && minprice.trim().length() > 0 && maxprice.trim().length() > 0) {
            sql += " and price between ? and ?";
            list.add(minprice);
            list.add(maxprice);
        }

        Object[] params = list.toArray();

        return runner.query(sql, new BeanListHandler<Product>(Product.class),
                params);
    }



    // 获取当前页数据
    public List<Product> findByPage(int currentPage, int currentCount,
                                    String category) throws SQLException {
        // 要执行的sql语句
        String sql = null;
        // 参数
        Object[] obj = null;
        // 如果category不为null,代表是按分类查找
        if (!"全部商品".equals(category)) {
//            "limit a,b" list the result from a(included). list number b result ;
            sql = "select * from products  where category=? limit ?,?";
            obj = new Object[] { category, (currentPage - 1) * currentCount,
                    currentCount, };
        } else {
            sql = "select * from products  limit ?,?";
            obj = new Object[] { (currentPage - 1) * currentCount,
                    currentCount, };
        }
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql, new BeanListHandler<Product>(Product.class),
                obj);
    }

    public List<Object[]> getWeekHotProduct() throws SQLException {
        //language=MySQL
        String sql="SELECT products.id,products.name,products.imgurl,SUM(ordersitem.buynum) AS totalsalnum " +
                "FROM products " +
                "INNER JOIN orderitem " +
                "ON products.id=orderitem.product_id " +
                "INNER JOIN orders " +
                "ON orders.id=orderitem.order_id " +
                "WHERE orders.paystate=1 AND orders.ordertime > SUBDATE(NOW(),7) " +
                "GROUP BY products.id,products.name,products.imgurl " +
                "ORDER BY totalsalnum DESC " +
                "LIMIT 2";
        // SUBDATE() and DATESUB() has the same usage

//        String sql = "SELECT products.id,products.name, "+
//                " products.imgurl,SUM(orderitem.buynum) totalsalnum "+
//                " FROM orderitem,orders,products "+
//                " WHERE orderitem.order_id = orders.id "+
//                " AND products.id = orderitem.product_id "+
//                " AND orders.paystate=1 "+
//                " AND orders.ordertime > DATE_SUB(NOW(), INTERVAL 7 DAY) "+
//                " GROUP BY products.id,products.name,products.imgurl "+
//                " ORDER BY totalsalnum DESC "+
//                " LIMIT 0,2 ";

        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql, new ArrayListHandler());
    }

    //前台，用于搜索框根据书名来模糊查询相应的图书
    public List<Product> findBookByName(int currentPage, int currentCount,
                                        String searchfield) throws SQLException {
        //根据名字模糊查询图书
        String sql = "SELECT * FROM products WHERE name LIKE '%"+searchfield+"%' LIMIT ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
//		//用于分页查询的数据
//		Object obj = new Object[] { (currentPage - 1) * currentCount, currentCount };
        return runner.query(sql,
                new BeanListHandler<Product>(Product.class),currentPage-1,currentCount);
    }

    //前台搜索框，根据书名模糊查询出的图书总数量
    public int findBookByNameAllCount(String searchfield) throws SQLException {
        //language=MySQL
        String sql = "SELECT COUNT(*) FROM products WHERE name LIKE '%" + searchfield +"%'";
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        return queryRunner.query(sql,new ScalarHandler<Long>(),searchfield).intValue();
    }
}

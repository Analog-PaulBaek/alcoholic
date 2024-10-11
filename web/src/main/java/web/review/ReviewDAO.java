package web.review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {

	 // DB 연결 정보
	   private String url = "jdbc:oracle:thin:@localhost:1521:testdb";
	   private String user = "scott";
	   private String password = "tiger";

	   // DB 연결 메서드 (드라이버 로드 및 DB 연결)
	   private Connection dbcon() throws SQLException {
	   Connection con = null;
	       try {
	           Class.forName("oracle.jdbc.driver.OracleDriver");
	           con = DriverManager.getConnection(url, user, password);
	           if(con != null) System.out.println("db connected");
	       } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	       }
	       return con;
	   }

	   //페이징 리스트 구하기 ArrayList<Review>
	   public ArrayList<Review> listPaging(int currentPage, int pageSize){	//1page 에 10 개  
		   Connection con  = null;
		   PreparedStatement pst = null;
		   ResultSet rs = null;
		   int start =1;
		   int end =5;
		   
		   String sql = "SELECT * FROM (SELECT review_no, user_id, product_no, item_no, contents, rating, created_at,"
		   		+ " ROW_NUMBER() OVER (ORDER BY review_no DESC) AS rn FROM reviewtbl) WHERE rn BETWEEN ? AND ?";
		   ArrayList<Review> list = new ArrayList<>();
		   try {
			con = dbcon();
			start = (currentPage-1)*pageSize+1;
			end = currentPage * pageSize ;
			pst = con.prepareStatement(sql);
			pst.setInt(1, start);
			pst.setInt(2, end);
			
			rs = pst.executeQuery();
			while (rs.next()) {
				String review_no = rs.getString(1);
				String user_id = rs.getString(2);
				String product_no = rs.getString(3);
				String item_no = rs.getString(4);
				String contents = rs.getString(5);
				String rating = rs.getString(6);
				String created_at = rs.getString(7);
				
				Review review = new Review(review_no, user_id, product_no ,item_no,contents,rating, created_at);
				list.add(review);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return list;
	   }
	   
	   
	   //전체레코드 수 구하기 
	   public int selectTotalCnt() {
			Connection con;
			PreparedStatement pst =null;
			ResultSet rs =null;
			String sql = "select count(*) from reviewtbl";	// 전체레코드 수 구하기
			int rowTotalCnt=0;
			
			try {
				con = dbcon();
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();
				if(rs.next()) {
					rowTotalCnt = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rowTotalCnt;
		}
	   
	   
	 //리뷰 상세 조회
	   public Review selectOne(String review_no) {
		   Connection con = null;
		   Review review = new Review();
		   String sql = "select * from reviewtbl where review_no=?";
		   PreparedStatement pst = null;
		   ResultSet rs = null;
		   try {
			con = dbcon();
			pst =con.prepareStatement(sql);
			pst.setString(1, review_no);
			rs = pst.executeQuery();
			if(rs.next()) {
				String review_no1 = rs.getString("review_no");
				System.out.println("Retrieved review_no: " + review_no1); // 로그 추가
				String user_id = rs.getString("user_id");
				String product_no = rs.getString("product_no");
				String item_no = rs.getString("item_no");
				String contents = rs.getString("contents");
				String rating = rs.getString("rating");
				String created_at = rs.getString("created_at");
				 // 로그 추가
			    System.out.println("Retrieved user_id: " + user_id);
			    System.out.println("Retrieved product_no: " + product_no);
			    System.out.println("Retrieved item_no: " + item_no);
			    System.out.println("Retrieved contents: " + contents);
			    System.out.println("Retrieved rating: " + rating);
			    System.out.println("Retrieved created_at: " + created_at);
				
				review = new Review(review_no1, user_id,  product_no, item_no, contents, rating,created_at);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("쿼리 실행 중 오류 발생: " + e.getMessage());
		}finally {
         close(rs, pst, con);
     }
		   return review;
	   }
	   
	   
	   
	// 상세조회 한 뒤 수정 가능 => 수정하면 update 
	   public int update (Review review){
		   int row = 0;
		   Connection con =null;
		   PreparedStatement pst =null;
		   String sql ="update  reviewtbl set  contents =?, rating=?  where review_no= ? ";
		   try {
			con = dbcon();
			pst = con.prepareStatement(sql);
			pst.setString(1, review.getContents());
			pst.setString(2, review.getRating());
			pst.setString(3, review.getReview_no());
			
	        row =pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        close(pst, con);  // 연결 종료는 finally 블록에서 수행
	    }
		   return row;
	   }
	   
		//리뷰 삭제   
	   public int delete(String review_no) {
		   int row = 0;
		   Connection con = null;		    
		   PreparedStatement pst =null;

		   
		   try {
			   con = dbcon();
			   //1.리뷰 삭제
			   String sql = "delete reviewtbl where review_no=?";
			   pst = con.prepareStatement(sql);
			   pst.setString(1, review_no);
			   row = pst.executeUpdate();
			   
			   //2.리뷰 번호 재조정
			   String updateSql = "UPDATE reviewtbl SET review_no = review_no - 1 WHERE review_no > ?";
			    pst= con.prepareStatement(updateSql);
			    pst.setString(1, review_no);
			    pst.executeUpdate();
		   		   
		   close( pst, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return row;
	   }
	   
	   
	   //리뷰추가
	   public void addReview(Review review) {
		   String sql = "INSERT INTO reviewtbl (review_no, user_id, product_no, item_no, contents, rating) VALUES (review_no_seq.NEXTVAL, ?, ?, ?, ?, ?)";

		    Connection con = null;
		    PreparedStatement pst = null;
		    try {
		    	con = dbcon();
		    	pst = con.prepareStatement(sql);
		    
		        pst.setString(1, review.getUser_id());
		        pst.setString(2, review.getProduct_no());
		        pst.setString(3, review.getItem_no());
		        pst.setString(4, review.getContents());
		        pst.setString(5, review.getRating());

		        pst.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }finally {
		        // 자원 해제
		        if (pst != null) {
		            try {
		                pst.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (con != null) {
		            try {
		                con.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		}
	   

	   
	   //자원해제
	   public void close( AutoCloseable ...a) {
			for( AutoCloseable  item : a) {
			   try {
				item.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	   
	   
	   public static void main(String[] args) {
		   ReviewDAO dao = new ReviewDAO();
			//ArrayList<Review> r = dao.selectAllReview();
			//for (Review review : r) {
			//	System.out.println(review);
			//}
			ArrayList<Review> list = dao.listPaging(1, 10);
			System.out.println(list);
		   
			//추가
//		   Review r = new Review("1","2","3","4","5");
//		   dao.addReview(r);
//		   System.out.println(r);
	}

}
package page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.MailSender;
import util.StringUtil;
//회원가입 내용을 보여줄 이미지
public class MamberPage extends JPanel{
	JPanel p_form;  //입력양식을 묶을 패널
	JLabel la_id, la_pass, la_email;
	JTextField t_id;
	JPasswordField t_pass;
	JTextField t_email;
	JButton bt_regist;
	AppMain appMain;
	MailSender mailSender;
	
	public MamberPage(AppMain appMain) {
		this.appMain=appMain;
		mailSender=new MailSender();
		this.setBackground(Color.orange);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));	
		
		p_form=new JPanel();
		la_id=new JLabel("ID");
		la_pass=new JLabel("Pass");
		la_email=new JLabel("Email");
		t_id=new JTextField();
		t_pass=new JPasswordField();
		t_email=new JTextField();
		bt_regist=new JButton("회원가입");
		
		p_form.setPreferredSize(new Dimension(500, 160));
		
		Dimension d=new Dimension(230, 25);
		la_id.setPreferredSize(d);
		la_pass.setPreferredSize(d);
		la_email.setPreferredSize(d);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_email.setPreferredSize(d);
		
		p_form.add(la_id);
		p_form.add(t_id);
		p_form.add(la_pass);
		p_form.add(t_pass);
		p_form.add(la_email);
		p_form.add(t_email);
		p_form.add(bt_regist);
		
		add(p_form);
		
		//등록 버튼에 리스너 연결
		bt_regist.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int result=regist();
				if(result>0) {
					JOptionPane.showMessageDialog(MamberPage.this, "등록성공");
					
					//이메일 발송(유저가 존재하는 이메일을 넣었을 경우)
					mailSender.sendMail(t_email.getText());
				}
			}
		});
	}
	//등록
	public int regist() {
		PreparedStatement pstmt=null;
		
		//? : 바인드 변수를 처리하고 싶을 때 사용하는 기호 -> 바인드 : 속도를 향상시키기 위한거
		String sql="insert into member(member_idx, id, pass, email)";
		sql+=" values(seq_member.nextval, ?, ?, ?)";
		int result=0;
		try {
			pstmt=appMain.con.prepareStatement(sql);
			//물음표의 값을 먼저 지정한 후 쿼리를 수행해야 함
			//바인드 변수의 순번은 1부터 시작함
			pstmt.setString(1, t_id.getText());  //id
			pstmt.setString(2, StringUtil.getCovertedPass(new String(t_pass.getPassword())));  //pass
			pstmt.setString(3, t_email.getText());  //email
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.release(pstmt);
		}
		return result;
	}
}

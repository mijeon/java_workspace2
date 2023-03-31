package com.edu.shop.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.edu.shop.domain.Product;
import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.model.repository.ProductDAO;
import com.edu.shop.model.repository.SubCategoryDAO;
import com.edu.shop.model.repository.TopCategoryDAO;
import com.edu.shop.model.table.ProductModel;
import com.edu.shop.util.DBManager;
import com.edu.shop.util.ImageManager;
import com.edu.shop.util.StringUtil;

//디자인
public class AdminMain extends JFrame implements ActionListener {
	TopCategoryDAO topcategoryDAO;
	SubCategoryDAO subCategoryDAO;
	public ProductDAO productDAO;
	DBManager dbManager = DBManager.getInstance();

	// 서쪽영역
	JPanel p_west;
	JComboBox<String> box_top; // 상위카테고리
	JComboBox<String> box_sub; // 하위카테고리
	JTextField t_name; // 상품명
	JTextField t_brand; // 브랜드명
	JTextField t_price; // 가격
	JPanel preview; // 이미지 미리보기 영역 145, 145
	JTextField t_url; // 이미지의 웹 주소
	JButton bt_preview; // 이미지 미리보기 버튼
	JButton bt_regist; // 등록 버튼

	// 센터영역
	JPanel p_center; // 북쪽과 센터로 구분되어질 패널
	JPanel p_search; // 검색기능이 올려질 패널
	JComboBox<String> box_category; // 검색 구분
	JTextField t_keyword; // 검색어
	JButton bt_search; // 검색 버튼
	ProductModel model; // jtable이 표 구성에 참조할 객체
	JTable table;
	JScrollPane scroll;

	// 동쪽영역
	JPanel p_east;
	JComboBox<String> box_top2; // 상위카테고리
	JComboBox<String> box_sub2; // 하위카테고리
	JTextField t_name2; // 상품명
	JTextField t_brand2; // 브랜드명
	JTextField t_price2; // 가격
	JPanel preview2; // 이미지 미리보기 영역
	JTextField t_url2; // 이미지의 웹 주소
	JButton bt_preview2; // 이미지 미리보기 버튼
	JButton bt_edit; // 수정 버튼
	JButton bt_del; // 삭제 버튼

	// 하위카테고리 선택 시 담아놓을 pk (subcategory_idx)
	List<Integer> subIdxList = new ArrayList<Integer>();
	
	//동쪽영역용
	List<Integer> subIdxList2 = new ArrayList<Integer>();

	String dir = "C:/java_workspace2/data/shop/product/";
	String filename; // 서쪽 영역에서 미리보게될 이미지명
	Image image; // 서쪽 영역에서 미리보기될 이미지
	Image image2; // 동쪽 영역에서 미리보기될 이미지
	
	Product currentProduct;  //현재 보고있는 상품

	public AdminMain() {
		topcategoryDAO = new TopCategoryDAO();
		subCategoryDAO = new SubCategoryDAO();
		productDAO = new ProductDAO();

		// 서쪽영역
		p_west = new JPanel();
		box_top = new JComboBox<String>();
		box_sub = new JComboBox<String>();
		t_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField("0"); // 가격 실수방지 차원
		preview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				g2.drawImage(image, 0, 0, 145, 145, AdminMain.this);
			}
		};
		t_url = new JTextField();
		bt_preview = new JButton("가져오기");
		bt_regist = new JButton("등록");

		// 서쪽 디자인
		p_west.setPreferredSize(new Dimension(150, 500));
		p_west.setBackground(Color.white);
		Dimension d = new Dimension(140, 20);
		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		preview.setPreferredSize(new Dimension(145, 145));
		t_url.setPreferredSize(d);

		// 부착
		add(p_west, BorderLayout.WEST);
		p_west.add(box_top);
		p_west.add(box_sub);
		p_west.add(t_name);
		p_west.add(t_brand);
		p_west.add(t_price);
		p_west.add(preview);
		p_west.add(t_url);
		p_west.add(bt_preview);
		p_west.add(bt_regist);

		// 센터영역
		p_center = new JPanel();
		p_search = new JPanel();
		box_category = new JComboBox<String>();
		t_keyword = new JTextField(15);
		bt_search = new JButton("검색");
		table = new JTable(model = new ProductModel(this)); // 7, 6
		scroll = new JScrollPane(table);

		// 센터 디자인
		scroll.setPreferredSize(new Dimension(550, 500));

		// 부착
		add(p_center);
		p_center.add(p_search);
		p_search.add(box_category);
		p_search.add(t_keyword);
		p_search.add(bt_search);
		p_center.add(scroll);

		// 동쪽영역
		p_east = new JPanel();
		box_top2 = new JComboBox<String>();
		box_sub2 = new JComboBox<String>();
		t_name2 = new JTextField("제품명");
		t_brand2 = new JTextField("브랜드");
		t_price2 = new JTextField("가격");
		preview2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, 145, 145);
				
				g2.drawImage(image2, 0, 0, 145, 145, AdminMain.this);
			}
		};
		t_url2 = new JTextField();
		bt_preview2 = new JButton("가져오기");
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		// 동쪽 디자인
		p_east.setPreferredSize(new Dimension(150, 500));
		p_east.setBackground(Color.white);
		Dimension d2 = new Dimension(140, 20);
		box_top2.setPreferredSize(d2);
		box_sub2.setPreferredSize(d2);
		t_name2.setPreferredSize(d2);
		t_brand2.setPreferredSize(d2);
		t_price2.setPreferredSize(d2);
		preview2.setPreferredSize(new Dimension(145, 145));
		t_url2.setPreferredSize(d2);

		// 부착
		add(p_east, BorderLayout.EAST);
		p_east.add(box_top2);
		p_east.add(box_sub2);
		p_east.add(t_name2);
		p_east.add(t_brand2);
		p_east.add(t_price2);
		p_east.add(preview2);
		p_east.add(t_url2);
		p_east.add(bt_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);

		getTopList();

		setSize(900, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
		
		//서쪽 영역에 이벤트 처리
		box_top.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// System.out.println(e.getItem());
					int topCategory_idx = topcategoryDAO.getTopcategoryIdx((String) e.getItem());
					System.out.println(topCategory_idx);

					// 하위 카테고리 출력
					getSubList(box_sub, subIdxList, topCategory_idx);
				}
			}
		});
		
		//동쪽 영역에 이벤트 처리
		box_top2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// System.out.println(e.getItem());
					int topCategory_idx = topcategoryDAO.getTopcategoryIdx((String) e.getItem());
					System.out.println(topCategory_idx);

					// 하위 카테고리 출력
					getSubList(box_sub2, subIdxList2, topCategory_idx);
				}
			}
		});
		
		// 버튼들과 리스너 연결
		bt_regist.addActionListener(this);
		bt_preview.addActionListener(this);
		bt_search.addActionListener(this);
		bt_preview2.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

		// 마우스 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow(); // 유저가 선택한 행(층), 이 정보로 ArrayList의 index를 접근할 수 있음
				// int col=table.getSelectedColumn(); //유저가 선택한 열(호)
				// System.out.println("row는 "+row+"col는 "+col);
				String product_idx = (String) table.getValueAt(row, 2); // product_idx을 2열에 설정했으므로, 2로 고정함
				System.out.println("product_idx는 " + product_idx);
				getDetail(Integer.parseInt(product_idx));
			}
		});
	}

	// 선택한 레코드 한건 가져오기 (결과를 우측에 반영)
	public void getDetail(int product_idx) {
		Product product = productDAO.select(product_idx);
		currentProduct=product;  //현재 보고있는 상품 대입
		System.out.println(product);

		// 우측영역에 반영하기
		// 선택하길 원하는 index 추출
		// 1) TopCategory DTO에 들어있는 카테고리 이름을 추출
		SubCategory subCategory = product.getSubcategory();
		TopCategory topCategory = subCategory.getTopCategory();
		String topName = topCategory.getTopcategory_name();

		// 2) 추출한 이름이 콤보박스에 몇번째 한글과 동일한지 그 index 알아맞추기
		for (int i = 0; i < box_top2.getItemCount(); i++) {
			String value = box_top2.getItemAt(i); // 0-안내문구, 1-상의, ....
			if (value.equals(topName)) { // 동일한 이름이 발견된다면
				System.out.println(topName + "가" + i + "번째에서 발견됨");
				box_top2.setSelectedIndex(i); //상위카테고리 idx
			}
		}

		getSubList(box_sub2, subIdxList2, topCategory.getTopcategory_idx());

		// 하위카테고리 이름 추출
		String subName = subCategory.getSubcategory_name();

		// 2) 추출한 이름이 콤보박스에 몇번째 한글과 동일한지 그 index 알아맞추기
		for (int i = 0; i < box_sub2.getItemCount(); i++) {
			String value = box_sub2.getItemAt(i); // 0-안내문구, 1-상의, ....
			if (value.equals(subName)) { // 동일한 이름이 발견된다면
				System.out.println(subName + "가" + i + "번째에서 발견됨");
				box_sub2.setSelectedIndex(i);  //하위 카테고리 idx
			}
		}
		// 우측영역에 상품명, 브랜드, 가격 채우기
		t_name2.setText(product.getProduct_name());
		t_brand2.setText(product.getBrand());
		t_price2.setText(Integer.toString(product.getPrice()));
		
		//현재 선택한 사진
		filename=product.getFilename();

		// 우측영역에 사진 그리기
		try {
			image2 = ImageIO.read(new File(dir + product.getFilename()));
			preview2.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 상위 카테고리 가져오기
	public void getTopList() {
		// 서쪽 영역용
		List<TopCategory> topList = topcategoryDAO.selectAll();

		// 서쪽영역
		box_top.addItem("상위 카테고리 선택");
		for (TopCategory topCategory : topList) {
			box_top.addItem(topCategory.getTopcategory_name());
		}
		// 동쪽영역
		box_top2.addItem("상위 카테고리 선택");
		for (TopCategory topCategory : topList) {
			box_top2.addItem(topCategory.getTopcategory_name());
		}
	}

	// 하위 카테고리 가져오기
	public void getSubList(JComboBox box, List list, int topcategory_idx) {
		// 하의 -> 청바지, 면바지, 반바지...
		List<SubCategory> subList = subCategoryDAO.selectByTopCategory(topcategory_idx);
		System.out.println(subList.size());

		// 기존아이템 비우기
		box.removeAllItems(); // 서쪽이 될지 동쪽이 될지 알 수 없음
		list.removeAll(list);

		box.addItem("하위 카테고리 선택");
		for (int i = 0; i < subList.size(); i++) {
			// 해당 인덱스에서 요소로 들어있는 DTO 꺼내기
			SubCategory subCategory = subList.get(i);
			box.addItem(subCategory.getSubcategory_name());

			// 한글로된 아이템 이름만 출력하지 말고, 해당 pk도 보관하자
			list.add(subCategory.getSubcategory_idx());
		}
		// System.out.println("보관된 하위카테고리 idx 수는 "+subIdxList.size());
		System.out.println("보관된 하위카테고리 idx 수는 " + list);

	}

	public boolean downLoad(JTextField url_input) {
		// finally에서 닫을 예정이므로 try 문 밖으로 빼놓자
		InputStream is = null;
		FileOutputStream fos = null;
		boolean flag = false;

		try {
			// 로컬에 있는 이미지가 아닌 웹의 URL 상의 이미지를 로컬로 수집한 후 , 보유
			URL url = new URL(url_input.getText());
			System.out.println(url);
			is = url.openStream();

			// 파일 출력스트림이 생성할 빈 파일의 이름을 결정짓자
			String ext = StringUtil.getExtend(url.toString());

			filename = StringUtil.createFilename(url.toString());

			// System.out.println(ext);
			fos = new FileOutputStream(dir + filename);

			int data = -1;
			while (true) {
				data = is.read(); // 1byte 씩 읽기
				if (data == -1)
					break;
				// if문을 만나지 않았다면 data 내뱉기
				fos.write(data);
			}
			JOptionPane.showMessageDialog(this, "수집완료");
			flag = true;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			flag = false; // catch 문을 만나면 실패
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	//이미지 미리보기 (서, 동쪽 모두 처리)
	public void previewWidhURL(JTextField input_url, JPanel canvas) {
		try {
			//서쪽, 동쪽 텍스트 필드 중 어느것이 될지 결정되지 않음
			URL url=new URL(input_url.getText());
			if(canvas==preview) {  //서쪽 미리보기 패널이라면
				image=ImageIO.read(url);
			}else {  //동쪽 미리보기 패널이라면
				image2=ImageIO.read(url);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.repaint();  //미리보기가 어느쪽(서, 동쪽)인지 결정되지 않음
	}

	public void preview(JPanel canvas) {
		File file = new File(dir + filename); // imageIO.read() 메서드를 사용하기 위해
		try {
			if(canvas==preview) {
				image = ImageIO.read(file);
			}else {
				image2 = ImageIO.read(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.repaint(); // 서쪽패널 다시 그리기
	}

	// 모든 상품 레코드 가져오기(단, 하위카테고리와 조인된 상태로)
	public void getProductList() {
		//List productList = productDAO.selectAll();
		model.getProductList();
		table.updateUI();
	}

	// 상품 등록
	public void regist() {
		// Product DAO에게 insert 시키자, 객체는 한번만 생성하므로 계속 new 할 필요가 없음 (전역변수로 선언)

		// 유저가 지금 선택한 하위카테고리 콤보박스의 index 보유
		int index = box_sub.getSelectedIndex();

		int subcategory_idx = subIdxList.get(index - 1); // 콤보박스의 index-1
		String product_name = t_name.getText();
		String brand = t_brand.getText();
		int price = Integer.parseInt(t_price.getText());

		// productDAO.insert(subcategory_idx, product_name, brand, price, filename);
		// //filename은 왜 그대로 사용하는거야?.?

		Product product = new Product(); // empty 상태의 dto
		SubCategory subCategory = new SubCategory();
		product.setSubcategory(subCategory); // ....?

		subCategory.setSubcategory_idx(subcategory_idx); // ....?
		product.setProduct_name(product_name);
		product.setBrand(brand);
		product.setPrice(price);
		product.setFilename(filename);

		int result = productDAO.insert(product);
		if (result > 0) {
			JOptionPane.showMessageDialog(this, "등록성공");
			getProductList();
		}
	}
	//선택한 상품 1건 삭제
	public void del() {
		if(currentProduct==null) {
			JOptionPane.showMessageDialog(this, "선택된 상품이 없습니다\n 상품을 먼저 선택해 주세요");
		}else {
			int op=JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?");
			if(op==JOptionPane.OK_OPTION) {  //확인을 누르면
				//파일 삭제
				System.out.println(dir+currentProduct.getFilename());
				boolean result=ImageManager.deleteFile(dir+currentProduct.getFilename());
				
				//db레코더 삭제
				if(result) {
					int n=productDAO.delete(currentProduct.getProduct_idx());
					if(n>0) {
						JOptionPane.showMessageDialog(this, "삭제 성공");
						//Jtable은 ProductModel이 보유한 ProductList만 참조하고 있으므로
						//갱신된 내용을 보여주려면 결국 ProductList가 변경되어야 함 따라서,
						//db를 다시 조회한 후 ProductList를 재설정하면 됨 Jtabl.updateUI()
						getProductList();
						currentProduct=null;  //다시 아무것도 선택하지 않은 상태로 돌려놓음
						reset();
					}
				}
			}
		}
	}
	
	//삭제 후 우측 영역리셋
	public void reset() {
		//콤보박스들 초기화
		box_top2.setSelectedIndex(0);  //상위카테고리
		box_sub2.setSelectedIndex(0);  //하위카테고리
		
		//상품정보 초기화
		t_name2.setText("");
		t_brand2.setText("");
		t_price2.setText("0");
		image2=null;
		preview2.repaint();
	}
	
	//상품 수정
	public void update() {
		//사용자가 파일 삭제를 원한다면
		if(t_url2.getText().length()>15) {
			System.out.println("사진교체를 원함");
			boolean result=ImageManager.deleteFile(dir+filename);
			if(result) {
				downLoad(t_url2);  //지정한 주소로 다운로드 진행
				preview(preview2);  //우측영역에 그림 보여주기
			}
		}else {
			//System.out.println("사진교체를 안원함");
			//파일 교체를 희망하지 않으면 기존의 이름을 유지
			filename=currentProduct.getFilename();
		}
		
		//기존 파일 삭제 후 새로운 파일 적용
		//ImageManager.deleteFile(dir+filename);
		System.out.println(dir+filename);
		
		//db update
		Product product=new Product();  //null
		SubCategory subCategory=new SubCategory();  //null
		product.setSubcategory(subCategory);  //참조관계
		
		int subcategory_idx=subIdxList2.get(box_sub2.getSelectedIndex()-1);  //선택한 콤보박스 index-1
		subCategory.setSubcategory_idx(subcategory_idx);
		product.setProduct_name(t_name2.getText());
		product.setBrand(t_brand2.getText());
		product.setPrice(Integer.parseInt(t_price2.getText()));
		product.setFilename(filename);
		product.setProduct_idx(currentProduct.getProduct_idx());
		
		int n=productDAO.update(product);
		if(n>0) {
			//refresh
			getProductList();
		}
		
		//refresh
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj.equals(bt_regist)) {
			//regist();
		} else if (obj.equals(bt_preview)) {
			//previewWidhURL(t_url, preview);
			
		} else if (obj.equals(bt_search)) {

		} else if (obj.equals(bt_preview2)) {
			previewWidhURL(t_url2, preview2);
			
		} else if (obj.equals(bt_edit)) {
			if(currentProduct!=null) {  //선택한 상품이 맞다면
				//update();  //상품 정보 수정
			}else {
				JOptionPane.showMessageDialog(this, "수정하실 상품을 선택해 주세요");
			}
		} else if (obj.equals(bt_del)) {
			del();
		}
	}

	public static void main(String[] args) {
		new AdminMain();
	}
}

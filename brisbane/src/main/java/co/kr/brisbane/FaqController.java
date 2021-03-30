package co.kr.brisbane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import model.faq.FaqDto;

@Controller
public class FaqController {
	@Autowired // setter�� �ڵ�
	private SqlSession sqlSession1;

	//�۾��� ��,���,����
	@RequestMapping("/faqWriteForm.do")
	public String WriteForm(Model model,String num,String ref,
			String re_step,String re_level,String pageNum,String question, String answer) {

		if(num==null) {//���� ����
			num="0";//�۹�ȣ
			ref="1";//�� �׷�
			re_step="0";//�� ����
			re_level="0";//�� ����
			
		}
		
		model.addAttribute("answer",answer);
		model.addAttribute("question",question);
		model.addAttribute("pageNum",pageNum);//request.setAttribute()
		model.addAttribute("num",new Integer(num));
		model.addAttribute("ref",new Integer(ref));
		model.addAttribute("re_step",new Integer(re_step));
		model.addAttribute("re_level",new Integer(re_level));
		

		//return "board/writeForm";//�� �̸� ���� views/ writeForm.jsp
		return ".main.faq.faqWriteForm";//�� �̸� ���� views/ writeForm.jsp
	}
	

	//DB�� �۾���
		@RequestMapping(value="faqWritePro.do",method=RequestMethod.POST)
		public String writeP(@ModelAttribute("faqDto") FaqDto faqDto,
				HttpServletRequest request) throws IOException,NamingException{

			int maxNum=0;//�ִ� �۹�ȣ, ���� ����
			if(sqlSession1.selectOne("faq.faqnumMax") != null) {
				//�ִ� �۹�ȣ�� null�ƴϸ� , �ִ� �۹�ȣ�� ������
				maxNum=sqlSession1.selectOne("faq.faqnumMax");
			}

			if(maxNum!=0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ� 
				maxNum=maxNum+1;//�ִ� �۹�ȣ +1 �� ���ۼ� , ��� �׷����� �Ϸ��� 
			}else {
				maxNum=1;//�۹�ȣ 1���� �ۼ� (ó�� ���̸�), ��� �׷����� ����Ϸ���
			}

			String ip=request.getRemoteAddr();//ip���Ѵ� 
			faqDto.setIp(ip);//setter �۾�
			
			if(faqDto.getNum() != 0) {//����̸�
				//��� ���� �ֱ� ��ġ Ȯ��
				sqlSession1.update("faq.reStep",faqDto);
				faqDto.getQuestion();
				faqDto.getAnswer();
				faqDto.getNum();
				faqDto.setRe_step(faqDto.getRe_step() + 1);//�ۼ���+1
				faqDto.setRe_level(faqDto.getRe_level() + 1);//�� ���� +1
				
				
			}else {//�����϶�
				faqDto.getQuestion();
				faqDto.getAnswer();
				faqDto.setRef(new Integer(maxNum));//�� �׷�
				faqDto.setRe_step(new Integer(0));// �� ���� 0
				faqDto.setRe_level(new Integer(0));//�� ���� 0
				faqDto.getNum();
		
			}

			sqlSession1.insert("faq.insertfaqBoard",faqDto);//insert

			return "redirect:faqList.do";//response.sendRedirect("lsit.jsp)�� ����
		}

		@RequestMapping("faqList.do")
		public String list(Model model, String pageNum) 
				throws IOException,NamingException{

			if(pageNum==null) {pageNum="1";}
		
			int pageSize=10;
			int currentPage=Integer.parseInt(pageNum);
			int startRow=(currentPage-1)*pageSize+1;//���������� ù���� row ���Ѵ� 
			int endRow=currentPage*pageSize;//�� �������� ������ row�� ���Ѵ� 

			int count=0;//�� �ۼ� �������� 
			int pageBlock=10;//���� 10�� �������� ���´� 
			
			count=sqlSession1.selectOne("faq.faqselectCount");//�� �ۼ� ��� 

			int number = count-(currentPage-1)*pageSize;
		     int pageCount=count/pageSize+(count%pageSize==0?0:1);
		       int startPage=(currentPage/10)*10+1;//����������             
		       int endPage=startPage+pageBlock-1;
			
			//int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� �������� ���ϱ�

			HashMap<String,Integer> map=new HashMap<String,Integer>();
			map.put("start", startRow-1);//������ġ ,mysql�� 0���� ���� 
			map.put("cnt",pageSize);//�� ���� (10����)
			


		
			//             1+10-1=10 end������


			List<FaqDto> list=sqlSession1.selectList("faq.faqselectList",map);

			model.addAttribute("currentPage",currentPage);
			model.addAttribute("startRow",startRow);
			model.addAttribute("endRow",endRow);

			model.addAttribute("pageBlock",pageBlock);
			model.addAttribute("pageCount",pageCount);

			model.addAttribute("startPage",startPage);
			model.addAttribute("endPage",endPage);
		
			model.addAttribute("count",count);
			model.addAttribute("pageSize",pageSize);

			model.addAttribute("num",number);
			model.addAttribute("list",list);//����� ������ 

			//return "board/list";//�� ����
			return ".main.faq.faqList";//�� ����
		}
		


		 @RequestMapping("faqContent.do")
		   public String content(Model model,String num,String pageNum,String ref, String re_step,String re_level )
		   throws IOException,NamingException{
			 
			  
			   int num1=Integer.parseInt(num);
			 
		
			
			   sqlSession1.update("faq.faqcount",num1);//��ȸ�� ����
			   
			   FaqDto faqDto=sqlSession1.selectOne("faq.getfaqBoard",num1);
			   
			   String content=faqDto.getContent();
			   content=content.replace("\n","<br>");
			   model.addAttribute("content",content);
			  
			   model.addAttribute("re_level",re_level);
			   model.addAttribute("re_step",re_step);
			   model.addAttribute("ref", ref);
			   model.addAttribute("num",new Integer(num1));
			   model.addAttribute("pageNum",pageNum);
			   model.addAttribute("faqDto",faqDto);
			 
			   //return "board/content";//�丮��,
			   return ".main.faq.faqContent";//�丮��,
			   
		   }
		 
		   //�ۼ��� ��
		   @RequestMapping("faqUpdate.do")
		   public ModelAndView updateForm(String num, String pageNum)
		   throws IOException,NamingException{
			   int num1=Integer.parseInt(num);
			   FaqDto faqDto=sqlSession1.selectOne("faq.getfaqBoard",num1);
			   
			   ModelAndView mv=new ModelAndView();
			   mv.addObject("num",new Integer(num1));
			   mv.addObject("pageNum",pageNum);
			   mv.addObject("faqDto",faqDto);
			   //mv.setViewName("board/updateForm");//��
			   mv.setViewName(".main.faq.faqUpdate");//��
			   
			   return mv;
		   }
		   //DB�� ����
		   @RequestMapping(value="faqUpdatePro.do",method=RequestMethod.POST)
		   public ModelAndView faqUpdatePro(FaqDto faqDto,String pageNum,String num)
		   throws IOException,NamingException{
			   sqlSession1.update("faq.faqupdatePro",faqDto);//�ۼ���
			   
			  ModelAndView mv=new ModelAndView();
			  mv.addObject("num", num);
			  mv.addObject("pageNum", pageNum);
			  mv.setViewName("redirect:faqList.do");
			  return mv;
		   }//updateForm.jsp ���鷯����
		   
		   
		   //DB�� ����
		   @RequestMapping("faqDelete.do")
		   public String delete(Model model,String num, String pageNum) {
			   sqlSession1.delete("faq.faqdeletePro",new Integer(num));
			   model.addAttribute("pageNum",pageNum);
			   return "redirect:faqList.do";
		   }
		
}

		

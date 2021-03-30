package co.kr.brisbane;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;//HashMap ���
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;//Mybatis ���

import model.board.BoardDto;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.naming.NamingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BoardController {
   
   @Autowired // setter�� �ڵ�
   private SqlSession sqlSession;
   
   //�۾��� ��,���,����
   @RequestMapping("/boardWriteForm.do")
   public String writeF(Model model,String num,String ref, 
         String re_step,String re_level,String pageNum) {
      
      if(num==null) {//���� ����
         num="0";//�۹�ȣ
         ref="1";//�� �׷�
         re_step="0";//�� ����
         re_level="0";//�� ����
      }
      
      model.addAttribute("pageNum",pageNum);//request.setAttribute()
      model.addAttribute("num",new Integer(num));
      model.addAttribute("ref",new Integer(ref));
      model.addAttribute("re_step",new Integer(re_step));
      model.addAttribute("re_level",new Integer(re_level));
      
      
      //return "board/writeForm";//�� �̸� ���� views/ writeForm.jsp
      return ".main.board.boardWriteForm";//�� �̸� ���� views/ writeForm.jsp
   }
   
   //DB�� �۾���
   @RequestMapping(value="WritePro.do",method=RequestMethod.POST)
   public String writePro(@ModelAttribute("boardDto") BoardDto boardDto,
         HttpServletRequest request) throws IOException,NamingException{
   
      int maxNum=0;//�ִ� �۹�ȣ, ���� ����
      if(sqlSession.selectOne("board.boardnumMax") != null) {
         //�ִ� �۹�ȣ�� null�ƴϸ� , �ִ� �۹�ȣ�� ������
         maxNum=sqlSession.selectOne("board.boardnumMax");
      }
      
      if(maxNum!=0) {//�ִ� �۹�ȣ�� 0�� �ƴϸ� 
         maxNum=maxNum+1;//�ִ� �۹�ȣ +1 �� ���ۼ� , ��� �׷����� �Ϸ��� 
      }else {
         maxNum=1;//�۹�ȣ 1���� �ۼ� (ó�� ���̸�), ��� �׷����� ����Ϸ���
      }
      
      String ip=request.getRemoteAddr();//ip���Ѵ� 
      boardDto.setIp(ip);//setter �۾�
      
      if(boardDto.getNum() != 0) {//����̸�
         //��� ���� �ֱ� ��ġ Ȯ��
         sqlSession.update("board.boardreStep",boardDto);
         boardDto.setRe_step(boardDto.getRe_step() + 1);//�ۼ���+1
         boardDto.setRe_level(boardDto.getRe_level() + 1);//�� ���� +1
      }else {//�����϶�
         boardDto.setRef(new Integer(maxNum));//�� �׷�
         boardDto.setRe_step(new Integer(0));// �� ���� 0
         boardDto.setRe_level(new Integer(0));//�� ���� 0
      }
      
      sqlSession.insert("board.boardinsertBoard",boardDto);//insert
      
      return "redirect:boardList.do";//response.sendRedirect("lsit.jsp)�� ����
   }
   
   @RequestMapping("boardList.do")
   public String listboard(Model model, String pageNum) 
         throws IOException,NamingException{
	   
      
      if(pageNum==null) {pageNum="1";}
      
      int pageSize=10;
      int currentPage=Integer.parseInt(pageNum);
      int startRow=(currentPage-1)*pageSize+1;//���������� ù���� row ���Ѵ� 
      int endRow=currentPage*pageSize;//�� �������� ������ row�� ���Ѵ� 
      
      int count=0;//�� �ۼ� �������� 
      int pageBlock=10;//���� 10�� �������� ���´� 
      
      count=sqlSession.selectOne("board.boardselectCount");//�� �ۼ� ��� 
      
      int number=count-(currentPage-1)*pageSize;//�۹��� �������� 
      //int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� �������� ���ϱ�
      
      HashMap<String,Integer> map=new HashMap<String,Integer>();
      map.put("start", startRow-1);//������ġ ,mysql�� 0���� ���� 
      map.put("cnt",pageSize);//�� ���� (10����)
      
      
      int pageCount=count/pageSize+(count%pageSize==0?0:1);//�� ������ ��
      //                 ��                       �Ǵٸ� ���ڵ� ��(31�� ��, �������� 4���� ������)
      int startPage=(currentPage/10)*10+1;//����������
      //                
      int endPage=startPage+pageBlock-1;
      //             1+10-1=10 end������
      
      
      List<BoardDto> list=sqlSession.selectList("board.boardselectList",map);
      
      model.addAttribute("currentPage",currentPage);
      model.addAttribute("startRow",startRow);
      model.addAttribute("endRow",endRow);
      
      model.addAttribute("pageBlock",pageBlock);
      model.addAttribute("pageCount",pageCount);
      
      model.addAttribute("startPage",startPage);
      model.addAttribute("endPage",endPage);
      
      model.addAttribute("count",count);
      model.addAttribute("pageSize",pageSize);
      
      model.addAttribute("number",number);
      model.addAttribute("list",list);//����� ������ 
      
      //return "board/list";//�� ����
      return ".main.board.boardList";//�� ����
   }
   
   //��ȸ�� ����, �۳��� ����
   @RequestMapping("boardContent.do")
   public String content(Model model,String num,String pageNum)
   throws IOException,NamingException{
	   int num1=Integer.parseInt(num);
	   sqlSession.update("board.boardreadCount",num1);//��ȸ�� ����
	   
	   BoardDto boardDto=sqlSession.selectOne("board.boardgetBoard",num1);
	   
	   String content=boardDto.getContent();
	   content=content.replace("\n","<br>");
	   model.addAttribute("content",content);
	   
	   model.addAttribute("num",num1);
	   model.addAttribute("pageNum",pageNum);
	   model.addAttribute("boardDto",boardDto);
	   
	   //return "board/content";//�丮��,
	   return ".main.board.boardContent";//�丮��,
	   
   }
   
   //�ۼ��� ��
   @RequestMapping("boardUpdate.do")
   public ModelAndView updateForm(String num, String pageNum)
   throws IOException,NamingException{
	   int num1=Integer.parseInt(num);
	   BoardDto boardDto=sqlSession.selectOne("board.boardgetBoard",num1);
	   
	   ModelAndView mv=new ModelAndView();
	   mv.addObject("pageNum",pageNum);
	   mv.addObject("boardDto",boardDto);
	   //mv.setViewName("board/updateForm");//��
	   mv.setViewName(".main.board.boardUpdate");//��
	   
	   return mv;
   }
   
   
   //DB�� ����
   @RequestMapping(value="UpdatePro.do",method=RequestMethod.POST)
   public ModelAndView updatePro(BoardDto boardDto,String pageNum)
   throws IOException,NamingException{
	   sqlSession.update("board.boardupdatePro",boardDto);//�ۼ���
	   
	  ModelAndView mv=new ModelAndView();
	  mv.addObject("pageNum", pageNum);
	  mv.setViewName("redirect:boardList.do");
	  return mv;
   }//updateForm.jsp ���鷯����
   
   //DB�� ����
   @RequestMapping("Delete.do")
   public String delete(Model model,String num, String pageNum) {
	   sqlSession.delete("board.boarddeletePro",new Integer(num));
	   model.addAttribute("pageNum",pageNum);
	   return "redirect:boardList.do";
   }
   
}//class end
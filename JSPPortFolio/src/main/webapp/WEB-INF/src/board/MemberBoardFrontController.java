package board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import board.command.*;

@WebServlet("*.bbs")
public class MemberBoardFrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberBoardFrontController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String cmdURI = requestURI.substring(contextPath.length());
        
        BoardCmd cmd = null;
        String viewPage = null;

        // 자유게시판 		
        if(cmdURI.equals("/boardList.bbs")){
            cmd = new BoardListCmd();
            cmd.execute(request, response);
            viewPage = "boardList.jsp";
        }
        // 회원게시판 		
        if(cmdURI.equals("/memberboardList.bbs")){
            cmd = new BoardListCmd();
            cmd.execute(request, response);
            viewPage = "memberboardList.jsp";
        }
        
        // 글쓰기
        if(cmdURI.equals("/memberboardWriteForm.bbs")){
            viewPage = "boardWrite.jsp";
        }
        
        // 글쓰기 확인		
        if(cmdURI.equals("/memberboardWrite.bbs")){
            cmd = new BoardWriteCmd();
            cmd.execute(request, response);
            viewPage = "memberboardList.bbs";
        }
        
        // 글 읽기
        if(cmdURI.equals("/memberboardRead.bbs")){
            cmd = new BoardReadCmd();
            cmd.execute(request, response);
            viewPage = "memberboardRead.jsp";
        }
        
        // 게시글 비밀번호 설정
        if(cmdURI.equals("/memberboardUpdatePassword.bbs")){
            cmd = new BoardUpdatePasswordCmd();
            cmd.execute(request, response);            
            viewPage = "memberboardUpdatePassword.jsp";
        }
        
        // 글쓰기 수정 확인
        if(cmdURI.equals("/memberboardUpdateCheck.bbs")){
            cmd = new BoardUpdateCheckCmd();
            cmd.execute(request, response);
            
            BoardUpdateCheckCmd checkCmd = (BoardUpdateCheckCmd) cmd;
            if (checkCmd.password_check){
                viewPage = "memberboardUpdateForm.bbs";                
            } else {
                viewPage = "memberboardUpdateError.bbs";
            }
        }
        
        // 글 수정 에러    
        if(cmdURI.equals("/memberboardUpdateError.bbs")){
            viewPage = "memberboardUpdateError.jsp";
        }
        
        // 글 수정 화면 요청
        if(cmdURI.equals("/memberboardUpdateForm.bbs")){
            cmd = new BoardUpdateFormCmd();
            cmd.execute(request, response);
            viewPage = "memberboardUpdateForm.jsp";
        }
        
        // 글 수정 처리
        if(cmdURI.equals("/memberboardUpdate.bbs")){
            cmd = new BoardUpdateCmd();
            cmd.execute(request, response);
            viewPage = "memberboardList.bbs";
        }
        
        // 글 삭제 비밀번호 확인 화면 요청
        if(cmdURI.equals("/memberboardDeletePassword.bbs")){
            cmd = new BoardDeletePasswordCmd();
            cmd.execute(request, response);            
            viewPage = "memberboardDeletePassword.jsp";
        }
        
        // 글 삭제 확인 처리
        if(cmdURI.equals("/memberboardDeleteCheck.bbs")){
            cmd = new BoardDeleteCheckCmd();
            cmd.execute(request, response);
            
            BoardDeleteCheckCmd checkCmd = (BoardDeleteCheckCmd) cmd;
            if (checkCmd.password_check && checkCmd.reply_check){
                viewPage = "memberboardDelete.bbs";                
            } else {
                viewPage = "memberboardDeleteError.bbs";
            }
        }
        
        // 글 삭제 에러 화면 요청
        if(cmdURI.equals("/memberboardDeleteError.bbs")){
            viewPage = "memberboardDeleteError.jsp";
        }
        
        // 글 삭제 처리
        if(cmdURI.equals("/memberboardDelete.bbs")){
            cmd = new BoardDeleteCmd();
            cmd.execute(request, response);
            viewPage = "memberboardList.bbs";
        }
        
        // 게시글 검색 처리
        if(cmdURI.equals("/memberboardSearch.bbs")){
            cmd = new BoardSearchCmd();
            cmd.execute(request, response);
            viewPage = "memberboardSearchList.jsp";
        }
        
        // 답글 쓰기 화면 요청
        if(cmdURI.equals("/memberboardReplyForm.bbs")){
            cmd = new BoardReplyFormCmd();
            cmd.execute(request, response);
            viewPage = "memberboardReply.jsp";
        }
        
        // 답글 쓰기 처리
        if(cmdURI.equals("/memberboardReply.bbs")){
            cmd = new BoardReplyCmd();
            cmd.execute(request, response);
            viewPage = "memberboardList.bbs";
        }
        
    
        RequestDispatcher dis = request.getRequestDispatcher(viewPage);
        dis.forward(request, response);
    }
}

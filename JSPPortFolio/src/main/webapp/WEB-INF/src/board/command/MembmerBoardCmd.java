package board.command;

import javax.servlet.http.*;

public interface MemberBoardCmd {
	
	public void execute(HttpServletRequest request, HttpServletResponse response);

}

package com.wangw.web.admin;

import com.wangw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CommentEditController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String comments(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page",commentService.listAllComments(pageable));
        return "admin/comments";
    }

    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/comments";
    }
}

package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图控制器，用于跳转指定页面
 */
@Controller
@RequestMapping("view")
public class ViewController {

    @GetMapping("/{path}")
    public String view(@PathVariable String path, Model model){

        return path;
    }
}

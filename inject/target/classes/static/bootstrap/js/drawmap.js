 function drawMap(root) {
			$("#relationsGraph").empty();
			var width = $(document.body).width()/12*9;
			var height = $(document.body).height()/12*9;
			console.log("width:"+width);
			console.log("height:"+height);	
			var svg = d3.select("#relationsGraph").append("svg").attr("width", width)
					.attr("height", height);

			svg.append("text").style("font-size", "18px").attr("x", 10).attr(
					"y", 25);

			var copNum = 0;
			var copFlag = 0;
			/*类别为2，level=0*/
			for (var i = 0; i < root.Nodes.length; i++) {
				if (root.Nodes[i].Category == 0) {
					root.Nodes[i].level = 0;
				} else {
					copNum++;
				}
			}
			/*类别为1，level=1或2*/
			for (var i = 0; i < root.Nodes.length; i++) {
				if (root.Nodes[i].Category == 1) {
					root.Nodes[i].level = 1;
				}

			}
			for (var i = 0; i < root.Nodes.length; i++) {
				if (root.Nodes[i].Category == 2) {
					root.Nodes[i].level = 2;
				}

			}
			for (var i = 0; i < root.Nodes.length; i++) {
				if (root.Nodes[i].Category == 3) {
					root.Nodes[i].level = 3;
				}

			}
			for (var i = 0; i < root.Nodes.length; i++) {
				if (root.Nodes[i].Category == 4) {
					root.Nodes[i].level = 4;
				}
			}
			/*造节点数据*/
            var nodes = [];                
            var tempNodesIndex = [];
            for(var i=0; i<root.Nodes.length; i++){
                var tempR = 16;
                if(root.Nodes[i].Category==1){
                    tempR = 16;
                }else {
                    tempR = 16;
                }
                nodes.push({
                    "keyNo":root.Nodes[i].KeyNo,
                    "name" :root.Nodes[i].Name,
                    "category":root.Nodes[i].Category,
                    "r":tempR,
                    "level": root.Nodes[i].level
                });
                tempNodesIndex.push(root.Nodes[i].KeyNo);
            }
            tempNodesIndex.reverse();
            nodes.reverse();
            
            /*连线的数据*/
            var links = [];
            for(var i=0; i<root.Links.length; i++){
                links.push({
                    "source":tempNodesIndex.indexOf(root.Links[i].Source),
                    "target":tempNodesIndex.indexOf(root.Links[i].Target),
                    "relation":root.Links[i].Relation.toString()
                });
            }
            console.log(links);
            
            /*创建d3力学图*/
            var force = d3.layout.force()
                    .nodes(nodes)
                    .links(links)
                    .size([width,height])
                    .linkDistance(200)
                    .charge(-1500)
                    .start();
            /*连线*/
            var edges_line = svg.selectAll("line")
                    .data(links)
                    .enter()
                    .append("line")
                    .style("stroke","#aaa")
                    .style("stroke-width",1);
                    /*.attr("marker-end", function (d) {   /*添加箭头*/
                       /* if(d.target.r == 20){
                            return "url(#arrow)";
                        }else{
                            return "url(#arrow2)";
                        }
                    });
            /*关系*/
            var edges_text = svg.selectAll(".linetext")
                    .data(links)
                    .enter()
                    .append("text")
                    .attr("class","linetext")
                    .attr("fill","#bbb")
                    .text(function(d){
                        return d.relation;
                    });
            /*拖拽固定*/
            var drag = force.drag()    /*问题   ：   循环每个节点都固定*/
                    .on("dragstart",function(d){
                        d.fixed = true ;  //拖拽开始后设定被拖拽对象为固定
                    })
                    .on("dragend",function(d){
                        force.stop();
                    });

            /*节点圆圈*/
            var nodes_circle = svg.selectAll("circle")
                    .data(nodes)
                    .enter()
                    .append("circle")
                    .attr("class","node")
                    .attr("r",function(d){return d.r;})
                    .style("fill",function(d,i){
                        if(d.category==0){
                            return "#2db294";
                        }else{
                            return "#727cd0";
                        }
                    })
                    .style("cursor","pointer")
                    .on("mouseover", function (d) {
                        if(d.category == 0){
                            this.style.fill = "#209b7f";
                        }else {
                            this.style.fill = "#5d67c0";
                        }
                        mouseOver(d);
                    })
                    .on("mouseout", function(d){
                        if(d.category == 0){
                            this.style.fill = "#2db294";
                        }else {
                            this.style.fill = "#727cd0";
                        }
                        mouseOut(d);
                    })
                    .on("mousemove", function () {
                        nozoom();
                    })
                    .on("click",function(d,i){
                        if(d3.event.defaultPrevented) return;
                        if(d.category == 1){
                            showDetail(d.keyNo);
                        }
                    })
                    .call(drag);
            /*节点里面的文字*/
            var nodes_text = svg.selectAll(".nodetext")
                    .data(nodes)
                    .enter()
                    .append("g")
                    .attr("class","nodetext")
                    .attr("keyNo", function (d) {
                        return d.keyNo;
                    })
                    .style("cursor","pointer")
                    .on("mouseover", function (t) {
                        nodes_circle.style("fill",function(d){
                            if(t.keyNo == d.keyNo){
                                if(d.category==0){
                                    return "#209b7f";
                                }else{
                                    return "#5d67c0";
                                }
                            }else {
                                if(d.category==0){
                                    return "#2db294";
                                }else{
                                    return "#727cd0";
                                }
                            }
                        });
                        mouseOver(t);
                    })
                    .on("mouseout", function(d){
                        nodes_circle.style("fill",function(d){
                            if(d.category==0){
                                return "#2db294";
                            }else{
                                return "#727cd0";
                            }
                        });
                        mouseOut(d);
                    })
                    .on("mousemove", function () {
                        nozoom();           /*阻止冒泡传播*/
                    })
                    .on("click",function(d,i){
                        if(d3.event.defaultPrevented) return;
                        if(d.category == 0){
                            showDetail(d.keyNo);
                        }
                    })
                    .call(drag);
            
            /*圆圈里面添加文字*/
            nodes_text.each(function (d, i) {
                var lines = Math.ceil(d.name.length/5);
                var text="";
                var dx = 0;
                var dy = 0;
                for(var line=0; line<lines; line++){
                    if(d.category==2){
                        dx = 0-d.name.length*6;
                        dy = 2;
                        d3.select(this).append("text").attr("dx",dx).attr("dy",dy).text(d.name);
                    }else{
                        dx = -12*2.5;
                        if(line==(lines-1)){
                            dx = 0-6*(d.name.substr(line*5,5).length);
                        }
                        dy = 14*line-lines*2.5;
                        d3.select(this).append("text").attr("dx",dx).attr("dy",dy).text(d.name.substr(line*5, 5));
                    }
                    //text += "<text dx='"+dx+"' dy='"+dy+"'>"+ d.name.substr(line*5, 5)+"</text>";
                }
            });
            
            /*移上去连接线和圆圈变色*/
            function mouseOver(d){
                edges_line.each(function (line) {
                    if(line.source.keyNo== d.keyNo||line.target.keyNo== d.keyNo){
                        d3.select(this).style("stroke","#128bed");
                    }
                });
                edges_text.each(function (line) {
                    if(line.source.keyNo== d.keyNo||line.target.keyNo== d.keyNo){
                        d3.select(this).style("fill","#128bed");
                    }
                });

            }
            /*移出去连接线和圆圈颜色变回去*/
            function mouseOut(d){
                edges_line.each(function (line) {
                    if(line.source.keyNo== d.keyNo||line.target.keyNo== d.keyNo){
                        d3.select(this).style("stroke","#aaa");
                    }
                });
                edges_text.each(function (line) {
                    if(line.source.keyNo== d.keyNo||line.target.keyNo== d.keyNo){
                        d3.select(this).style("fill","#bbb");
                    }
                });
            }
            /*改变位置的变化*/
            force.on("tick", function(){
                //限制结点的边界
                nodes.forEach(function(d,i){
                	
                    d.x = d.x < d.r ? d.r : d.x ;
                    d.x = d.x  > width- d.r ? width- d.r : d.x ;
                    d.y = d.y < d.r ? d.r : d.y ;
                    d.y = d.y > height- d.r ? height- d.r : d.y;

                });
                //更新连接线的位置
                edges_line.attr("x1",function(d){ return d.source.x; });
                edges_line.attr("y1",function(d){ return d.source.y; });
                edges_line.attr("x2",function(d){ return d.target.x; });
                edges_line.attr("y2",function(d){ return d.target.y; });
                //更新连接线上文字的位置
                edges_text.attr("x",function(d){ return (d.source.x + d.target.x) / 2- d.relation.length/2*10 ; });
                edges_text.attr("y",function(d){ return (d.source.y + d.target.y) / 2-5 ; });
                edges_text.attr("transform", function (d) {
                    var x = (d.source.x + d.target.x) / 2;
                    var y = (d.source.y + d.target.y) / 2;
                    var angle = 0;
                    if(d.target.x-d.source.x==0){
                        if(d.target.y> d.source.y){
                            angle = 90;
                        }else {
                            angle = -90
                        }
                    }else{
                        angle = Math.atan((d.target.y-d.source.y)/(d.target.x-d.source.x)) * 180 / Math.PI;
                    }
                    return "rotate("+angle+" "+x+","+y+")";
                })
                //更新结点图片和文字
                nodes_circle.attr("cx",function(d){console.log(d.x);return d.x; });
                nodes_circle.attr("cy",function(d){ return d.y; });

                nodes_text.attr("x",function(d){
                    d3.select(this).selectAll("text").attr("x", d.x);
                    return d.x
                });
                nodes_text.attr("y",function(d){
                    d3.select(this).selectAll("text").attr("y", d.y);
                    return d.y ;
                });
            });
            /*初始停止*/
            setTimeout(function(){
                force.start();
                for (var i = 0; i <root.Nodes.length; ++i) force.tick();	//执行n个力布局收敛节拍之后停止位置计算
                force.stop();
            },0);
            
		}
		//........................
		// 获取url中的参数
        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  /*空格或&开头   等号后边非&开头没有或者有&结尾*/
          
            var r = window.location.search.substr(1).match(reg);  /*跳转页面*/ 
            if (r != null) {return decodeURIComponent(r[2]);} return null;
        }
		
		//阻止冒泡传播
        function nozoom() {
            d3.event.preventDefault();
        }
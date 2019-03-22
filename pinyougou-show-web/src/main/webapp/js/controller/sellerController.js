//控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	

	$controller('baseController',{$scope:$scope});//继承

	//读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
				function(response){
					$scope.list=response;
				}			
		);
	}    

	//分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
				function(response){
					$scope.list=response.rows;	
					$scope.paginationConf.totalItems=response.total;//更新总记录数
				}			
		);
	}

	//查询实体 
	$scope.findOne=function(id){
		sellerService.findOne(id).success(
				function(response){
					$scope.entity= response;					
				}
		);				
	}
	
	//修改商家数据
	$scope.updateAll=function(){
		sellerService.updateAll($scope.entity).success(
				function(response){
					if(response.success){
						alert(response.message);
					}else{
						alert(response.message);
					}				
				}
		);				
	}

	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
				function(response){
					if(response.success){
						//重新查询 
						$scope.reloadList();//重新加载
					}else{
						alert(response.message);
					}
				}		
		);				
	}

	//注册
	$scope.add=function(){				
		sellerService.add($scope.entity).success(
				function(response){
					if(response.success){
						//成功跳转页面
						location.href="shoplogin.html";
					}else{
						alert(response.message);
					}
				}		
		);				
	} 

	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
				function(response){
					if(response.success){
						$scope.reloadList();//刷新列表
						$scope.selectIds=[];
					}						
				}		
		);				
	}

	$scope.searchEntity={};//定义搜索对象 

	//搜索
	$scope.search=function(page,rows){			
		sellerService.search(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list=response.rows;	
					$scope.paginationConf.totalItems=response.total;//更新总记录数
				}			
		);
	}

	//修改密码 
	$scope.updatePwd=function(p1,p2){
		if(p1==p2){
			/*$scope.findOne($scope.entity.sellerId).success(
					function
			);
			*/
			sellerService.findPwdBySellerId($scope.entity).success(
					function(response){
						if(response.success){
							$scope.entity.password=p1;
							sellerService.updatePwd($scope.entity).success(
									function(response){
										if(response.success){
											alert(response.message+"请重新登陆！");
											window.parent.frames.location.href="../logout";
											/*top.location.href="../logout";*/
										}else{
											alert(response.message);
											location.reload();
										}
									}
							);	
						}else{
							alert(response.message);
							location.reload();
						}
					}
			);
		}else{
			alert("新密码不一致且不能为空！");
		}
	}

});	

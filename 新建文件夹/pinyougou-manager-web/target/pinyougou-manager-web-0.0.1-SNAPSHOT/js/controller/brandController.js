app.controller('brandController',function($scope,$controller,$timeout,brandService){
    		
			$controller('baseController',{$scope:$scope});
	
			$scope.findAll=function(){
    			brandService.findAll().success(
    				function(response){
    					$scope.list=response;
    			});
    		}
    		
    		
    		/* 分页 */
    		$scope.findPage=function(page,size){
    			brandService.findPage(page,size).success(
    				function(response){
    					$scope.list=response.rows;
    					$scope.paginationConf.totalItems=response.total;
    				}		
    			);	
    		}
    		
    		$scope.add=function(){
    			var Object = null;
    			
    			if($scope.entity.id!=null){
    				Object = brandService.update($scope.entity);
    			}else{
    				Object = brandService.add($scope.entity);
    			}
    			
    			Object.success(
    				function(response){
    					if(response.success){
    						 $scope.reloadList();
    					}else{
    						alert(response.message);
    					}
    				}
    			);
    		} 
    		
    		$scope.findOne=function(id){
    			brandService.findOne(id).success(
    					function(response){
    						$scope.entity=response;
    					}
    			);
    		}
    		
    		
    		$scope.dele=function(){
    			brandService.dele($scope.selectIds).success(
    					function(response){
    						if(response.success){
    							$scope.reloadList();
    							$scope.selectIds=[];
    						}else{
    							alert(response.message);
    						}
    					}
    			);
    		}
    		
    		$scope.searchEntity={};
    		//搜索栏数据分页  延时查询
    		$scope.search=function(page,size){
    			if(($scope.searchEntity.name!=null&&$scope.searchEntity.name!="")||($scope.searchEntity.firstChar!=null&&$scope.searchEntity.firstChar!="")){
    				$timeout(function(){  /* 延时搜索优化策略 */
        				brandService.search(page, size, $scope.searchEntity).success(
            				function(response){	
            					$scope.list=response.rows;
            					$scope.paginationConf.totalItems=response.total;
            				}		
            			);	
        			},500);
    			}else{
    				brandService.search(page, size, $scope.searchEntity).success(
            				function(response){
            					$scope.list=response.rows;
            					$scope.paginationConf.totalItems=response.total;
            				}		
            			);	
    			}
    			/*$scope.test = ($scope.searchEntity.name!=null&&$scope.searchEntity.name!="");*/
    		}
    		
    		
    	});
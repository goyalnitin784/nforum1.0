(function($) {
    /*
    Validation Singleton
    */
	
    var Validation = function() {
        var rules = {   
        		infDOB : {
    				check : function(value) {
    					if(value) {
    						firstTravelDate = getFirstTravelDate();
							var yearNow = firstTravelDate.getYear();
							var ieVersion = checkIEVersion();
							if(ieVersion >= 9.0 || ieVersion == -1)
								yearNow += 1900;
							var monthNow = firstTravelDate.getMonth()+1;
    					    var dateNow = firstTravelDate.getDate();	  
    						var dateOfBirth = value.split("/");
    						var yearDob = dateOfBirth[2];
    						var monthDob = dateOfBirth[1];
    						var dateDob = dateOfBirth[0];   		
    					    var yearDiff = yearNow - yearDob;
    						var monthDiff = monthNow - monthDob;
    						var dateDiff = dateNow - dateDob;
    						if(yearDiff>2) {
    					    	return false;
    					    }
    					    else if(yearDiff == 2) {
    					    	if(monthDiff>0) {
    					    		return false;
    					    	}
    					    	else if(monthDiff==0 && dateDiff>0) {
    					    		return false;
    					    	}
    					    }
    	                }
    					return true;
    				},
    				msg : "* 0-2 Years w.r.t. Travel Date."
    			},
    			chdDOB : {
    				check : function(value) {
    					if(value) {
    						firstTravelDate = getFirstTravelDate();
    						var yearNow = firstTravelDate.getYear();
							var ieVersion = checkIEVersion();
							if(ieVersion >= 9.0 || ieVersion == -1)
								yearNow += 1900;
    					    var monthNow = firstTravelDate.getMonth()+1;
    					    var dateNow = firstTravelDate.getDate();	  
    						var dateOfBirth = value.split("/");
    						var yearDob = parseInt(dateOfBirth[2], 10);
    						var monthDob = parseInt(dateOfBirth[1], 10);
    						var dateDob = parseInt(dateOfBirth[0], 10);
    						var min_child_year = yearDob+2;
    						var max_child_year = yearDob+12;
    						if(yearNow < min_child_year || yearNow > max_child_year){
    							return false;
    						}else{
    							if(yearNow == min_child_year){
    								if(monthNow < monthDob || (monthNow == monthDob && dateNow < dateDob)){
    									return false;
    								}
    							}else if(yearNow == max_child_year){
    								if(monthNow > monthDob || (monthNow == monthDob && dateNow > dateDob)){
    									return false;
    								}
    							}
    						}
    	                }
    					return true;
    				},
    				msg : "* 2-12 Years w.r.t.Travel Date."
    			},
    			adtDOB : {
    				check : function(value) {
    					if(value) {
    						firstTravelDate = getFirstTravelDate();
    						var yearNow = firstTravelDate.getYear();
							var ieVersion = checkIEVersion();
							if(ieVersion >= 9.0 || ieVersion == -1)
								yearNow += 1900;
    					    var monthNow = firstTravelDate.getMonth()+1;
    					    var dateNow = firstTravelDate.getDate();	  
    						var dateOfBirth = value.split("/");
    						var yearDob = dateOfBirth[2];
    						var monthDob = dateOfBirth[1];
    						var dateDob = dateOfBirth[0];   		
    					    var yearDiff = yearNow - yearDob;
    					    var monthDiff = monthNow - monthDob;
    						var dateDiff = dateNow - dateDob;
    					    if(yearDiff<12) {
    					    	return false;
    					    }
    					    else if(yearDiff == 12) {
    					    	if(monthDiff<0) {
    					    		return false;
    					    	}
    					    	else if(monthDiff==0 && dateDiff<0) {
    					    		return false;
    					    	}
    					    }
    	                }
    					return true;
    				},
    				msg : "* > 12 Years w.r.t. Travel Date."
    			},
            email : {
               check: function(value) {
				   value = value.toLowerCase(); /* WHAT EVER EMAIL ID TYPE W'LL BE CONVERTED TO LOWERCASE */
				   /*
				   var filter = "/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/";
				   
				   */
                   if(value) {
						var filter = /^(([^<>()[\]\\.,;:\s@#%~^*\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/;
    return filter.test(value);
                   }
               },
               msg : "Enter a valid e-mail address."
            },

			
			regType : {
               check: function(value,fieldID,type,len) {
				   var type = type.replace(/[[\]]/g,'');
				   if (len!= undefined) {
					   var checkLen = parseInt(len.replace(/[[\]]/g,''));
				   }

				   if (type=="alpha") {
						if(value) {
						  return /^\s*[a-zA-Z\s]+\s*$/.test(value);
					   }
					}else if (type=="phone" && checkLen==10) {
						if(value) {
						  return /[0-9]*\.?[0-9]{10,10}/.test(value);
					   }
						
					} else {
						if(value) {
						  return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
					   }
					}
               },
               msg : "Enter valid number."
            },
			
			regTypeMin : {
               check: function(value,fieldID,type,maxLen) {
				   var type = type.replace(/[[\]]/g,'');
				   
				   if (maxLen!= undefined) {
					   var checkMaxLen = parseInt(maxLen.replace(/[[\]]/g,''));
				   }
				   if(value.length<checkMaxLen) {
					   return false;
				   } else {
					   return true;
				   }
               },
               msg : "Enter valid number."
            },

			validRewardCard : {
				 check: function(value) {
					 if(prefixValid) {
					   return true;
					} else {
					   return false;
					}
               },
				
			   msg : "Please enter valid reward card number."
				 
			},

            required : {
               check: function(value) {
                   if(value && value.indexOf("--") == -1 && value!="dd/mm/yyyy")
                       return true;
                   else
                       return false;
               },
				
			   msg : "This field is required."
               
            },
			dob : {
				check:function(value){
					if(value == 'DOB') return false;
					else return true;
					},
				msg : "* required"
			},
			minChar: {
				check:function(value){
						if(value.length < 2)
						return false;
						else return true;
						},
					msg : "initial not allowed"
			
			},
			cityCheck : {
               check: function(value) {
                   if(value=="Enter city/airport" || value=="No match found for the search")
                       return false;
                   else
                       return true;
               },
				
			   msg : "This field is required."
               
            },
			sameValue : {
               check: function(value,fieldID,elmVal_1,elmVal_2,DT) {
				   var valueFROM = elmVal_1.replace(/[[\]]/g,'');
				   var valueTO = elmVal_2.replace(/[[\]]/g,'');
				   
				   if($("#"+valueTO).val().indexOf("--") == -1 && $("#"+valueFROM).val().indexOf("--")==-1 && 
					   $("#"+valueTO).val()==$("#"+valueFROM).val()) {
						return false;
				   } else {
						return true;
				   }	   
               },
				msg : "Select valid option."
            },

			DateDiff : {
               check: function(value,fieldID,elmVal_1,elmVal_2,DT) {
				   var __date1 = "";var __date2 = "";var __dateMargin = "";var __dateDiff="";
				   if (value!="") {
					   __date1 = elmVal_1.replace(/[[\]]/g,'');
					   
					   __date2 = elmVal_2.replace(/[[\]]/g,'');
					   //__dateMargin = DT.replace(/[[\]]/g,'');
					   
					   __dateMargin = DT;
					   __dateDiff = ($.dateParse.getDateDiff($.dateParse.parseDate("dd/mm/yy", $("#"+__date2).val()),$.dateParse.parseDate("dd/mm/yy", $("#"+__date1).val())));
					   
				   }
                  
				  /*
				  console.log("DT... " + dateMargin);
				  console.log("dateDiff... " + dateDiff);
                  */  

				   if(!value || __dateDiff>=__dateMargin) {
                       return false;
				   } else {
                       return true;
				   }
               },
				
			   msg : "Date required."
            },
			abtCheckAmount: {
				check:function(value){
						abtOfflineAmt = parseInt($("#selectedcost").text().replace(/\,/g,''));
						if(!value && value <=0 || value>abtOfflineAmt || (isNaN(value))) {
						   return false;
					   } else {
						   return true;
					   }
					},
					msg : "Amount should equal/less than total payable amount and more than zero."
			
			},

			checkReward: {
				check:function(value){
						var totAmount = getYouPayShoppingCartAmount();
						if(value < 0 || value>totAmount || (isNaN(value))) {
						   return false;
					   } else {
						   return true;
					   }
						},
					msg : "Reward amount should be equal/less than total payable amount."
			
			},

			CreditCard : {
				check: function(value,fieldID,CardType) {
					var val ="";
					$("input[creditCheck=true]").not(":hidden").each(function(e){
						val+=$(this).val();
					});
					
					value = val;
					var cardName = CardType.replace(/[[\]]/g,'');
					var cards = new Array();
					cards [0] = {cardName: "Visa", lengths: "16", prefixes: "4"};
					cards [1] = {cardName: "Master", lengths: "16", prefixes: "50,51,52,53,54,55"};
					cards [2] = {cardName: "Dinners", lengths: "14", prefixes: "300,301,302,303,304,305,36,38,55"};
					cards [3] = {cardName: "Amex", lengths: "15", prefixes: "34,37"};
					cards [4] = {cardName: "Maest", lengths: "16,19", prefixes: "5081,5020,5038,5893,6304,6759,6761,6762,6763,0604"};
					cards [5] = {cardName: "VisaElectron", lengths: "16", prefixes: "417500,4917,4913"};					
					cards [6] = {cardName: "SBI", lengths: "16", prefixes: ""};					
					
					var cardType = -1;
					for (var i=0; i<cards.length; i++) {
						if (cardName.toLowerCase() == cards[i].cardName.toLowerCase()) {
							cardType = i;
							//console.log("cardType : " + cardType);
							break;
						}
					}

					if (cardType == -1) { return false; } // card type not found
					value = value.replace (/[\s-]/g, ""); // remove spaces and dashes
					
					if (value.length == 0) { return false; } // no length

					var cardNo = value;
					var cardexp = /^[0-9]{13,19}$/;
					if (!cardexp.exec(cardNo)) { return false; } // has chars or wrong length

					cardNo = cardNo.replace(/\D/g, ""); // strip down to digits
					//console.log("cardNo : " + cardNo);
					
					var lengthValid = false;
					var prefixValid = false;
					var prefix = new Array ();
					var lengths = new Array ();

					prefix = cards[cardType].prefixes.split(",");
					for (i=0; i<prefix.length; i++) {
						var exp = new RegExp ("^" + prefix[i]);
						if (exp.test (cardNo)) prefixValid = true;
					}
					if (!prefixValid) { return false; } // invalid prefix

					lengths = cards[cardType].lengths.split(",");
					for (j=0; j<lengths.length; j++) {
						if (cardNo.length == lengths[j]) lengthValid = true;
					}
					if (!lengthValid) {return false;} // wrong length
					return true;
				},
				
				msg : "Enter valid credit card."
			},
			
			abtCreditCard : {
				check: function(value,fieldID,CardType) {
					var val ="";
					$("input[abtCreditCheck=true]").not(":hidden").each(function(e){
						val+=$(this).val();
					});
					
					value = val;
					var cardName = CardType.replace(/[[\]]/g,'');
					
					var cards = new Array();
					cards [0] = {cardName: "Visa", lengths: "6", prefixes: "4"};
					cards [1] = {cardName: "Master", lengths: "6", prefixes: "50,51,52,53,54,55"};
					cards [2] = {cardName: "Dinners", lengths: "6", prefixes: "300,301,302,303,304,305,36,38,55"};
					cards [3] = {cardName: "Amex", lengths: "6", prefixes: "34,37"};
					cards [4] = {cardName: "Maest", lengths: "6", prefixes: "5081,5020,5038,5893,6304,6759,6761,6762,6763,0604"};
					cards [5] = {cardName: "VisaElectron", lengths: "6", prefixes: "417500,4917,4913"};					
					cards [6] = {cardName: "SBI", lengths: "6", prefixes: ""};					
					
					var cardType = -1;
					for (var i=0; i<cards.length; i++) {
						if (cardName.toLowerCase() == cards[i].cardName.toLowerCase()) {
							cardType = i;
							//console.log("cardType : " + cardType);
							break;
						}
					}

					if (cardType == -1) { return false; } // card type not found
					value = value.replace (/[\s-]/g, ""); // remove spaces and dashes
					if (value.length == 0) { return false; } // no length

					var cardNo = value;
					var cardexp = /^[0-9]{6}$/;
					if (!cardexp.exec(cardNo)) { return false; } // has chars or wrong length

					cardNo = cardNo.replace(/\D/g, ""); // strip down to digits
					
					var lengthValid = false;
					var prefixValid = false;
					var prefix = new Array ();
					var lengths = new Array ();

					prefix = cards[cardType].prefixes.split(",");
					for (i=0; i<prefix.length; i++) {
						var exp = new RegExp ("^" + prefix[i]);
						if (exp.test (cardNo)) prefixValid = true;
					}
					
					if (!prefixValid) { return false; } // invalid prefix
					
					lengths = cards[cardType].lengths.split(",");
					for (j=0; j<lengths.length; j++) {
						if (cardNo.length == lengths[j]) lengthValid = true;
					}
					if (!lengthValid) {return false;} // wrong length
					return true;
				},
				
				msg : "Enter valid credit card."
			},
			
			
			multiFromToRequired :{
				check: function(value,fieldID,elmVal_1,elmVal_2,segCnt) {
					nofseg= $(segCnt).not(":hidden").length;
					multiCheck = false;
					
						for(var i=0;i<nofseg;i++){
							orgVal = $("#" + elmVal_1 + i).val();
							destVal = $("#" + elmVal_2 + i).val();
							if((orgVal && orgVal.indexOf("--") == -1) || (destVal && destVal.indexOf("--") == -1)){
								multiCheck = true;
								break;
							}
						}
					return multiCheck;
				},
				msg : "Please fill all the required fields to search for a flight."
			},
			multiCheckSeg : {
				check : function(value,fieldID,elmVal_1,elmVal_2,curelmVal){
					orgVal = $("#" + elmVal_1).val();
					destVal = $("#" + elmVal_2).val();
					
					orgFlg = rules.required.check(orgVal);
					destFlg = rules.required.check(destVal);
					
					// if source is empty and destination entered
					if(!orgFlg && destFlg){
						if(elmVal_1 == curelmVal){
							return false;
						}
					}else if(orgFlg && !destFlg){ // if source entered and destination is empty
						if(elmVal_2 == curelmVal){
							return false;
						}
					}
					return true;
					
				},
				msg : "This field is required."
			},
			multiDateReq :{
				check : function(value,fieldID,elmVal_1,elmVal_2,elmVal_3){
					orgVal = $("#" + elmVal_1).val();
					destVal = $("#" + elmVal_2).val();
					dateVal = $("#" + elmVal_3).val();
					
					orgFlg = rules.required.check(orgVal);
					destFlg = rules.required.check(destVal);
					
					if(orgFlg || destFlg){
						dateFlg = rules.required.check(dateVal);
						if(!dateFlg){
							return false;
						}
					}
					return true;
					
				},
				msg : "This field is required."
			},
			multiDateDiff : {
				check : function(value,fieldID,elmVal_1,elmVal_2,elmVal_3,elmVal_4,sCnt){
					orgVal_1 = $("#" + elmVal_1+sCnt).val();
					orgVal_2 = $("#" + elmVal_1+(sCnt-1)).val();
					
					destVal_1 = $("#" + elmVal_2+sCnt).val();
					destVal_2 = $("#" + elmVal_2+(sCnt-1)).val();
							
					orgFlg_1 = rules.required.check(orgVal_1);
					orgFlg_2 = rules.required.check(orgVal_2);
					
					destFlg_1 = rules.required.check(destVal_1);
					destFlg_2 = rules.required.check(destVal_2);
					
					if(orgFlg_1 && orgFlg_2 && destFlg_1 && destFlg_2){
						return rules.DateDiff.check(value,fieldID,elmVal_3,elmVal_4,1);
					}
					return true;
					
				},
				msg : "Date required."
			},
			multiDuplicateValue : {
				check : function(value,fieldID,elmVal_1,elmVal_2,elmVal_3,sCnt,dCnt){
					
					orgVal_1 = $("#" + elmVal_1+sCnt).val();
					orgVal_2 = $("#" + elmVal_1+(sCnt-1)).val();
					
					destVal_1 = $("#" + elmVal_2+sCnt).val();
					destVal_2 = $("#" + elmVal_2+(sCnt-1)).val();
					
					dateVal_1 = $("#" + elmVal_3+dCnt).val();
					dateVal_2 = $("#" + elmVal_3+(dCnt-1)).val();
					
					orgFlg_1 = rules.required.check(orgVal_1);
					orgFlg_2 = rules.required.check(orgVal_2);
					
					destFlg_1 = rules.required.check(destVal_1);
					destFlg_2 = rules.required.check(destVal_2);
					
					dateFlg_1 = rules.required.check(dateVal_1);
					dateFlg_2 = rules.required.check(dateVal_2);
					
					if(orgFlg_1 && orgFlg_2 && destFlg_1 && destFlg_2 && dateFlg_1 && dateFlg_2){
						if(orgVal_1 == orgVal_2 && destVal_1 == destVal_2 && dateVal_1 == dateVal_2){
							return false;
						}
					}
					
									
				    return true;					
				},
				msg : "Duplicate segment search on same dates not permitted."
			},
			// rule which check whether the field contains default text written
			defaultInvalid :{
				check : function(value,elmId){
					elmLabelVal = $("#"+elmId).attr("myLabel");
					if(value == elmLabelVal){
						return false;
					}
					
					return true;
				},
				msg : "* required"
			},
			visaPrefDate :{
				check: function(value) {
                   if(value=="Your preferred date")
                       return false;
                   else
                       return true;
				},
				msg : "* required"
			},
			// rule to check whether check box is checked or not
			
			requiredCheckBox :{
				check : function(value,elmId){
					
					// if any one of the checkbox is unchecked
					if(!$("#"+elmId).is(":checked")){
						return false;
					}
					
					return true;
				},
				msg : "* required"
			},
			// rule to compare two checkbox are checked or not
			requiredChecked :{
				check : function(value,elmId_1,elmId_2){
					if(elmId_1 != undefined || elmId_1 != undefined) {
						if(($("#"+elmId_1).is(":checked")&& !$("#"+elmId_2).is(":checked")) ||
							(!$("#"+elmId_1).is(":checked")&& $("#"+elmId_2).is(":checked"))){
							return false;
						}
					}
					
					return true;
				},
				msg : "* required"
			},
			expiryDateCheck :{ // expiry date check 
				check : function(){
					val="";
					
					mnthElm = $("input[expMonth=true]:visible");
					yrElm = $("input[expYear=true]:visible");					
					
					elmLabel = mnthElm.attr("myLabel");
					
					expMonth = mnthElm.val();
					if(elmLabel == expMonth){
						return false;
					}	
					
					elmLabel = yrElm.attr("myLabel");
					expYear = yrElm.val();
					
					if(elmLabel == expYear){
						return false;
					}
					
					val+=expMonth+expYear;
					elmVal=val;
					
					maxLen = parseInt(mnthElm.attr("maxlength"))+parseInt(yrElm.attr("maxlength"))
					if(elmVal.length == 0){
						return false;
					}else if(elmVal.length < maxLen){
						return false;
					}
					
					var expVal = elmVal;
					var regExp = /[0-9]*$/;
					if (!regExp.exec(expVal)) { return false; } // has chars or wrong length
					
					today = new Date();
					month = today.getMonth();
					if (navigator.appName == 'Microsoft Internet Explorer'){
						year = today.getYear();
					}else{
						year = today.getYear()+1900;
					}
					month = eval(month + 1);
					
					expMonth = eval(expMonth);
					expYear = eval(expYear)+2000;
					
					if ((expYear < year) || (expMonth > 12 || expMonth < 1) || (expYear == year && expMonth < month)) {
						return false;
					}
					
					return true;
				},
				msg : " * Invalid Date"
			}
        } // end of rules
		
        var testPattern = function(value, pattern) {
            var regExp = new RegExp(pattern,"");
            return regExp.test(value);
        }

        return {
            addRule : function(name, rule) {
                rules[name] = rule;
            },
            getRule : function(name) {
                return rules[name];
            }
        }
    }

    /* 
    Form factory 
    */
    var Form = function(form) {
        var fields = [];
        form.find("[validation]").not(":hidden").each(function() {
            var field = $(this);
			
			/*--- 
				Note: below modification is done to have a check on payment page
				on click of paynow button on payment(review) page we do not want promocode to be validated
				it will be validated only on click of Apply/Validate button
			---*/
			doNotPush=0;
			if(form.attr("id") == "paymentBean" && field.attr("id") == "promoText"){
				doNotPush=1;
			}
			if(!doNotPush){
				if(field.attr('validation') != undefined && !(field.attr('id')=="datePickerDepart_dom2" && ($("input[checkVal=domCheck]:checked").val()=="O" || $("input[checkVal=intCheck]:checked").val()=="O")) && !(field.attr('id')=="landingdatepicker_int2" && !($('input[name=RoundTripCheck]').is(':checked')))) {
					fields.push(new Field(field));
				}
			}
            
        });
        
		this.fields = fields;
    };

    Form.prototype = {
        validate : function() {
            for(field in this.fields) {                
                checkMulti = this.fields[field].validate();	
				
				// if it was a multicity check where it checks whether user selected any data
				if(checkMulti){
					break;
				}
            }
        },
        isValid : function() {
            for(field in this.fields) {
                if(!this.fields[field].valid) {
                    //this.fields[field].field.focus();
                    return false;
                }
            }
            return true;
        }
    };
    
    /* 
    Field factory 
    */
    var Field = function(field) {
        this.field = field;
        this.valid = false;
        this.attach("blur");
		this.attach("focus");
    };

    Field.prototype = {
        attach : function(event) {
            var obj = this;			
			 if(event == "focus") {
                obj.field.bind("click",function() {
					//$(this).next(".errorlist").remove();
                    $("body").find("div.errorlist").remove();
                });
				
            }
        },

        validate : function() {
            var obj = this,
                field = obj.field,
				
				isMultiCheck = false;
				
				// split string ex: validation="required|sameValue[orgin,destination]"
                types = field.attr("validation").split("|");
				
                container = $("body");
                errors = [],
				
				fieldID = field.attr('id');
                if(fieldID != undefined) {
					curField = $("#"+fieldID);
				}
				fieldWidth = curField.width();
				fieldHeight = curField.height();
				var offset = curField.offset();
				
				promptTopPosition = offset.top + 10;
                promptleftPosition = offset.left+ 80;
				
				msgOverWrite = "";
				
				if(typeof field.attr('msgInfo') != 'undefined'){
					msgOverWrite = field.attr('msgInfo').split(",");
				}	
				
            field.next(".errorlist").remove();
			var ruleSplit = "";
            for (var type in types) {
				//ruleSplit = types[type].split(",");
				// split string ex: sameValue[origin,destination]
				ruleSplit = types[type].split(/\[|,|\]/);
				
				var rule = $.Validation.getRule(ruleSplit[0]);

				if(rule != undefined){
					if(!rule.check($.trim(field.val()),fieldID,ruleSplit[1],ruleSplit[2],ruleSplit[3],ruleSplit[4],ruleSplit[5])) {
						container.addClass("error");
						/*if(!msgOverWrite[type] || !field.val()) {
							errors.push(rule.msg);
						}else{
							errors.push(rule.msg.replace(rule.msg,msgOverWrite[type]));
						}*/
						if(msgOverWrite[type]){
							errors.push(rule.msg.replace(rule.msg,msgOverWrite[type]));
						}else{
							errors.push(rule.msg);
						}
						
						if(ruleSplit[0] == "multiFromToRequired"){// || ruleSplit[0] == "multiDateReq"){
							isMultiCheck = true;
						}
						break;
					}
				}
				
				
            }
			
			// call method to create the validation prompt
			errorlist = obj.createErrorPrompt(fieldID);
			
            if(errors.length) {
                obj.field.unbind("keyup");
                obj.attach("keyup");
                field.after(errorlist.empty());
                for(error in errors) {
					
					//errorlist.empty();
                    //errorlist.append(errors[error]);
					//container.append(errorlist);
					errorlist.html(errors[error]);
					errorlist.animate({
						"opacity": 0.87
					});
                }
                obj.valid = false;
				field.valid = false;
            } else {
                errorlist.remove();
                container.removeClass("error");
                obj.valid = true;
				field.valid = false;
            }
			
			return isMultiCheck;
        },
		
		createErrorPrompt : function(fieldID){
			errorClass = "errorlist",
			errorlist = $('<div>');
			errorlist.addClass(errorClass);
			errorlist.attr('id',"error-"+fieldID);
			errorlist.css({
					//"top": promptTopPosition,
					//"left": promptleftPosition,
					//"left" : "30px",
					"position" : "absolute",
					"opacity": 0
			});
			
			return errorlist;
		}
    }
    
    /*
    Validation extends jQuery prototype
    */
    $.extend($.fn, {
        validation : function() {
            var validator = new Form($(this));
           
            validator.validate();
           /* $(this).bind("click", function(e) {
                validator.validate();
				
				if(!validator.isValid()) {
                    e.preventDefault();
                }
            });*/
			$.data($(this)[0], 'validator', validator);
        }
    });
    $.Validation = new Validation();
})(jQuery);


(function($) { // Hide the namespace
	var PROP_NAME = 'Date Parsing';
	
	function __ParseDate() {
		this._uuid = new Date().getTime(); // Unique identifier seed		
		this._defaults = {};
	}
	
	$.extend(__ParseDate.prototype, {
		version: '1.0', // Current version
		
		/* Retrieve the instance data for the target control.
		   @param  target  (element) the target input field or division or span
		   @return  (object) the associated instance data
		   @throws  error if a jQuery problem getting data */
		_getInst: function(target) {
			try {
				return $.data(target, PROP_NAME);
			}
			catch (err) {
				throw 'Missing instance data for this date parsing';
			}
		},
	
		/* Extract all possible characters from the date format.
		   @param  inst  (object) the instance settings for this datepicker
		   @return  (string) the set of characters allowed by this format */
		_possibleChars: function (inst) {
			var dateFormat = $.dateParse._get(inst, 'dateFormat');
			var chars;
			var literal = false;
			// Check whether a format character is doubled
			var lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
				if (matches)
					iFormat++;
				return matches;
			};
			for (var iFormat = 0; iFormat < dateFormat.length; iFormat++)
				if (literal)
					if (dateFormat.charAt(iFormat) == "'" && !lookAhead("'"))
						literal = false;
					else
						chars += dateFormat.charAt(iFormat);
				else
					switch (dateFormat.charAt(iFormat)) {
						case 'd': case 'm': case 'y': case '@':
							chars += '0123456789';
							break;
						case 'D': case 'M':
							return null; // Accept anything
						case "'":
							if (lookAhead("'"))
								chars += "'";
							else
								literal = true;
							break;
						default:
							chars += dateFormat.charAt(iFormat);
					}
			return chars;
		},
		
		/* Set as beforeShowDay function to prevent selection of weekends.
		   @return  ([boolean, string]) is this date selectable?, what is its CSS class? */
		noWeekends: function(date) {
			return [(date.getDay() || 7) < 6, ''];
		},
	
		/* Set as calculateWeek to determine the week of the year based on the ISO 8601 definition.
		   @param  date  (Date) the date to get the week for
		   @return  (number) the number of the week within the year that contains this date */
		iso8601Week: function(date) {
			var checkDate = new Date(date.getTime());
			// Find Thursday of this week starting on Monday
			checkDate.setDate(checkDate.getDate() + 4 - (checkDate.getDay() || 7));
			var time = checkDate.getTime();
			checkDate.setMonth(0); // Compare with Jan 1
			checkDate.setDate(1);
			return Math.floor(Math.round((time - checkDate) / 86400000) / 7) + 1;
		},
	
		/* Parse a string value into a date object.
		   @return  (Date) the extracted date value or null if value is blank */
		parseDate: function (format, value, settings) {
			if (format == null || value == null)
				throw 'Invalid arguments';
			value = (typeof value == 'object' ? value.toString() : value + '');
			if (value == '')
				return null;
			settings = settings || {};
			var shortYearCutoff = settings.shortYearCutoff || this._defaults.shortYearCutoff;
			shortYearCutoff = (typeof shortYearCutoff != 'string' ? shortYearCutoff :
				new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
			var dayNamesShort = settings.dayNamesShort || this._defaults.dayNamesShort;
			var dayNames = settings.dayNames || this._defaults.dayNames;
			var monthNamesShort = settings.monthNamesShort || this._defaults.monthNamesShort;
			var monthNames = settings.monthNames || this._defaults.monthNames;
			var year = -1;
			var month = -1;
			var day = -1;
			var doy = -1;
			var literal = false;
			// Check whether a format character is doubled
			var lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
				if (matches)
					iFormat++;
				return matches;
			};
			// Extract a number from the string value
			var getNumber = function(match) {
				lookAhead(match);
				var size = (match == '@' ? 14 : (match == '!' ? 20 :
					(match == 'y' ? 4 : (match == 'o' ? 3 : 2))));
				var digits = new RegExp('^\\d{1,' + size + '}');
				var num = value.substring(iValue).match(digits);
				if (!num)
					throw 'Missing number at position ' + iValue;
				iValue += num[0].length;
				return parseInt(num[0], 10);
			};
			// Extract a name from the string value and convert to an index
			var getName = function(match, shortNames, longNames) {
				var names = (lookAhead(match) ? longNames : shortNames);
				for (var i = 0; i < names.length; i++) {
					if (value.substr(iValue, names[i].length) == names[i]) {
						iValue += names[i].length;
						return i + 1;
					}
				}
				throw 'Unknown name at position ' + iValue;
			};
			// Confirm that a literal character matches the string value
			var checkLiteral = function() {
				if (value.charAt(iValue) != format.charAt(iFormat))
					throw 'Unexpected literal at position ' + iValue;
				iValue++;
			};
			var iValue = 0;
			for (var iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal)
					if (format.charAt(iFormat) == "'" && !lookAhead("'"))
						literal = false;
					else
						checkLiteral();
				else
					switch (format.charAt(iFormat)) {
					case 'd':
						day = getNumber('d');
						break;
					case 'D':
						getName('D', dayNamesShort, dayNames);
						break;
					case 'o':
						doy = getNumber('o');
						break;
					case 'w':
						getNumber('w');
						break;
					case 'm':
						month = getNumber('m');
						break;
					case 'M':
						month = getName('M', monthNamesShort, monthNames);
						break;
					case 'y':
						year = getNumber('y');
						break;
					case '@':
						var date = new Date(getNumber('@'));
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case '!':
						var date = new Date((getNumber('!') - this._ticksTo1970) / 10000);
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "'":
						if (lookAhead("'"))
							checkLiteral();
						else
							literal = true;
						break;
					default:
						checkLiteral();
				}
			}
			if (iValue < value.length)
				throw 'Additional text found at end';
			if (year == -1)
				year = new Date().getFullYear();
			else if (year < 100)
				year += (shortYearCutoff == -1 ? 1900 : new Date().getFullYear() -
					new Date().getFullYear() % 100 - (year <= shortYearCutoff ? 0 : 100));
			if (doy > -1) {
				month = 1;
				day = doy;
				do {
					var dim = this._getDaysInMonth(year, month - 1);
					if (day <= dim)
						break;
					month++;
					day -= dim;
				} while (true);
			}
			var date = this._daylightSavingAdjust(new Date(year, month - 1, day));
			if (date.getFullYear() != year || date.getMonth() + 1 != month || date.getDate() != day)
				throw 'Invalid date'; // E.g. 31/02/*
			return date;
		},
	
		/* Standard date formats. */
		ATOM: 'yy-mm-dd', // RFC 3339 (ISO 8601)
		COOKIE: 'D, dd M yy',
		ISO_8601: 'yy-mm-dd',
		RFC_822: 'D, d M y',
		RFC_850: 'DD, dd-M-y',
		RFC_1036: 'D, d M y',
		RFC_1123: 'D, d M yy',
		RFC_2822: 'D, d M yy',
		RSS: 'D, d M y', // RFC 822
		TICKS: '!',
		TIMESTAMP: '@',
		W3C: 'yy-mm-dd', // ISO 8601
	
		_ticksTo1970: (((1970 - 1) * 365 + Math.floor(1970 / 4) - Math.floor(1970 / 100) +
			Math.floor(1970 / 400)) * 24 * 60 * 60 * 10000000),
		formatDate: function (format, date, settings) {
			if (!date)
				return '';
			settings = settings || {};
			var dayNamesShort = settings.dayNamesShort || this._defaults.dayNamesShort;
			var dayNames = settings.dayNames || this._defaults.dayNames;
			var monthNamesShort = settings.monthNamesShort || this._defaults.monthNamesShort;
			var monthNames = settings.monthNames || this._defaults.monthNames;
			var calculateWeek = settings.calculateWeek || this._defaults.calculateWeek;
			// Check whether a format character is doubled
			var lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
				if (matches)
					iFormat++;
				return matches;
			};
			// Format a number, with leading zero if necessary
			var formatNumber = function(match, value, len) {
				var num = '' + value;
				if (lookAhead(match))
					while (num.length < len)
						num = '0' + num;
				return num;
			};
			// Format a name, short or long as requested
			var formatName = function(match, value, shortNames, longNames) {
				return (lookAhead(match) ? longNames[value] : shortNames[value]);
			};
			var output = '';
			var literal = false;
			if (date)
				for (var iFormat = 0; iFormat < format.length; iFormat++) {
					if (literal)
						if (format.charAt(iFormat) == "'" && !lookAhead("'"))
							literal = false;
						else
							output += format.charAt(iFormat);
					else
						switch (format.charAt(iFormat)) {
							case 'd':
								output += formatNumber('d', date.getDate(), 2);
								break;
							case 'D':
								output += formatName('D', date.getDay(), dayNamesShort, dayNames);
								break;
							case 'o':
								output += formatNumber('o',
									(date.getTime() - new Date(date.getFullYear(), 0, 0).getTime()) / 86400000, 3);
								break;
							case 'w':
								output += formatNumber('w', calculateWeek(date), 2);
								break;
							case 'm':
								output += formatNumber('m', date.getMonth() + 1, 2);
								break;
							case 'M':
								output += formatName('M', date.getMonth(), monthNamesShort, monthNames);
								break;
							case 'y':
								output += (lookAhead('y') ? date.getFullYear() :
									(date.getFullYear() % 100 < 10 ? '0' : '') + date.getFullYear() % 100);
								break;
							case '@':
								output += date.getTime();
								break;
							case '!':
								output += date.getTime() * 10000 + this._ticksTo1970;
								break;
							case "'":
								if (lookAhead("'"))
									output += "'";
								else
									literal = true;
								break;
							default:
								output += format.charAt(iFormat);
						}
				}
			return output;
		},
	
		/* Get a setting value, defaulting if necessary.
		   @param  inst  (object) the instance settings for this datepicker
		   @param  name  (string) the name of the property
		   @return  (any) the property's value */
		_get: function(inst, name) {
			return inst.settings[name] !== undefined ?
				inst.settings[name] : this._defaults[name];
		},
	
		/* A date may be specified as an exact value or a relative one.
		   @param  inst         (object) the instance settings for this datepicker
		   @param  date         (Date or number or string) the date or offset
		   @param  defaultDate  (Date) the date to use if no other supplied
		   @return  (Date) the decoded date */
		_determineDate: function(inst, date, defaultDate) {
			var offsetNumeric = function(offset) {
				var date = new Date();
				date.setDate(date.getDate() + offset);
				return date;
			};
			var offsetString = function(offset) {
				try {
					return $.dateParse.parseDate($.datepick._get(inst, 'dateFormat'),
						offset, $.dateParse._getFormatConfig(inst));
				}
				catch (e) {
					// Ignore
				}
				var date = (offset.toLowerCase().match(/^c/) ?
					$.dateParse._getDate(inst) : null) || new Date();
				var year = date.getFullYear();
				var month = date.getMonth();
				var day = date.getDate();
				var pattern = /([+-]?[0-9]+)\s*(d|w|m|y)?/g;
				var matches = pattern.exec(offset.toLowerCase());
				while (matches) {
					switch (matches[2] || 'd') {
						case 'd':
							day += parseInt(matches[1], 10); break;
						case 'w':
							day += parseInt(matches[1], 10) * 7; break;
						case 'm':
							month += parseInt(matches[1], 10);
							day = Math.min(day, $.dateParse._getDaysInMonth(year, month));
							break;
						case 'y':
							year += parseInt(matches[1], 10);
							day = Math.min(day, $.dateParse._getDaysInMonth(year, month));
							break;
					}
					matches = pattern.exec(offset.toLowerCase());
				}
				return new Date(year, month, day);
			};
			date = (date == null ? defaultDate : (typeof date == 'string' ? offsetString(date) :
				(typeof date == 'number' ? (isNaN(date) || date == Infinity || date == -Infinity ?
				defaultDate : offsetNumeric(date)) : date)));
			date = (date && (date.toString() == 'Invalid Date' ||
				date.toString() == 'NaN') ? defaultDate : date);
			if (date) {
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
				date.setMilliseconds(0);
			}
			return this._daylightSavingAdjust(date);
		},
	
		/* Handle switch to/from daylight saving.
		   @return  (Date) the corrected date */
		_daylightSavingAdjust: function(date) {
			if (!date) return null;
			date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
			return date;
		},
	
		/* Provide the configuration settings for formatting/parsing.
		   @return  (object) the settings subset */
		_getFormatConfig: function(inst){
			return {
				shortYearCutoff: this._get(inst, 'shortYearCutoff'),
				dayNamesShort: this._get(inst, 'dayNamesShort'),
				dayNames: this._get(inst, 'dayNames'),
				monthNamesShort: this._get(inst, 'monthNamesShort'),
				monthNames: this._get(inst, 'monthNames')
			};
		},
	
		/* Format the given date for display.
		   @return  (string) formatted date */
		_formatDate: function(inst, year, month, day) {
			if (!year)
				inst.dates[0] = new Date(inst.cursorDate.getTime());
			var date = (year ? (typeof year == 'object' ? year :
				this._daylightSavingAdjust(new Date(year, month, day))) : inst.dates[0]);
			return this.formatDate(this._get(inst, 'dateFormat'), date, this._getFormatConfig(inst));
		},
		
		getDateDiff : function (A, B) {
			return Math.round((A - B) / 86400000);
		}
	});	
	$.dateParse = new __ParseDate(); // Singleton instance
})(jQuery);
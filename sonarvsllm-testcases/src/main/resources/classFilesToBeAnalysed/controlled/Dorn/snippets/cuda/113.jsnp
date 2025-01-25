		
		regP = regH0.w;					//save the old H value
		regH0.w = max(regT, regE0.w);  	//adjust vecH value with vecE and vecShift
		regH0.w = max(regH0.w, regF);
				
		regT = sub_sat(regH0.w, cudaGapOE);	//calculate the new vecE
		regE0.w = max(regE0.w - cudaGapExtend, regT);
		regF = max(regF - cudaGapExtend, regT); //calculate the new vecShift;

		/*compute the vector segment starting from 8*/
		j += THREADS_PER_WARP;
		regSubScore = tex2D(InterPackedQueryPrf, j, res);  //to the upper-left direction
		regT = regP + regSubScore.x;
		regMaxH = max(regMaxH, regT);
		
		regP = regH1.x;					//save the old H value
		regH1.x = max(regT, regE1.x);  	//adjust vecH value with vecE and vecShift
		regH1.x = max(regH1.x, regF);
				
		regT = sub_sat(regH1.x, cudaGapOE);	//calculate the new vecE
		regE1.x = max(regE1.x - cudaGapExtend, regT);
		regF = max(regF - cudaGapExtend, regT); //calculate the new vecShift;

		/*compute the vector segment starting from 9*/
		regT = regP + regSubScore.y;
		regMaxH = max(regMaxH, regT);
		
		regP = regH1.y;					//save the old H value
		regH1.y = max(regT, regE1.y);  	//adjust vecH value with vecE and vecShift
		regH1.y = max(regH1.y, regF);
				
		regT = sub_sat(regH1.y, cudaGapOE);	//calculate the new vecE
		regE1.y = max(regE1.y - cudaGapExtend, regT);
		regF = max(regF - cudaGapExtend, regT); //calculate the new vecShift;
		
		/*compute the vector segment starting from 10*/
		regT = regP + regSubScore.z;
		regMaxH = max(regMaxH, regT);
		
		regP = regH1.z;					//save the old H value
		regH1.z = max(regT, regE1.z);  	//adjust vecH value with vecE and vecShift
		regH1.z = max(regH1.z, regF);
				
		regT = sub_sat(regH1.z, cudaGapOE);	//calculate the new vecE
		regE1.z = max(regE1.z - cudaGapExtend, regT);
		regF = max(regF - cudaGapExtend, regT); //calculate the new vecShift;
		
		/*compute the vector segment starting from 11*/
		regT = regP + regSubScore.w;
		regMaxH = max(regMaxH, regT);

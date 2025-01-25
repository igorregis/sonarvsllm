		regH0.y = max(regT, regE0.y);  	//adjust vecH value with vecE and vecShift
		regH0.y = max(regH0.y, regF);
				
		regT = sub_sat(regH0.y, cudaGapOE);	//calculate the new vecE
		regE0.y = max(regE0.y - cudaGapExtend, regT);
		regF = max(regF - cudaGapExtend, regT); //calculate the new vecShift;

		/*compute the vector segment starting from 6*/
		regT = regP + regSubScore.z;
		regMaxH = max(regMaxH, regT);

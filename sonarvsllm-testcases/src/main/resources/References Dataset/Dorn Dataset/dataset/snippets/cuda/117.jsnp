		
								float temp = commonValue * resDeriv.x;
								jointEntropyDerivative_X -= temp * jointLog;
								fixedEntropyDerivative_X -= temp * targetLog;
								movingEntropyDerivative_X -= temp * resultLog;
		
								temp = commonValue * resDeriv.y;
								jointEntropyDerivative_Y -= temp * jointLog;
								fixedEntropyDerivative_Y -= temp * targetLog;
								movingEntropyDerivative_Y -= temp * resultLog;
		
								temp = commonValue * resDeriv.z;
								jointEntropyDerivative_Z -= temp * jointLog;
								fixedEntropyDerivative_Z -= temp * targetLog;
								movingEntropyDerivative_Z -= temp * resultLog;
							} // O<t<bin
						} // t
					} // 0<r<bin
				} // r
		
				float NMI= c_NMI;
		           float temp = c_Entropies.z;
		           // (Marc) I removed the normalisation by the voxel number as each gradient has to be normalised in the same way
				gradValue.x = (fixedEntropyDerivative_X + movingEntropyDerivative_X - NMI * jointEntropyDerivative_X) / temp;
				gradValue.y = (fixedEntropyDerivative_Y + movingEntropyDerivative_Y - NMI * jointEntropyDerivative_Y) / temp;
				gradValue.z = (fixedEntropyDerivative_Z + movingEntropyDerivative_Z - NMI * jointEntropyDerivative_Z) / temp;
		
			}
		}
		voxelNMIGradientArray_d[targetIndex]=gradValue;

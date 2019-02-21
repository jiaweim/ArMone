/* 
 ******************************************************************************
 * File: IInspectPhosphoPeptidePair.java * * * Created on 02-18-2009
 *
 * Copyright (c) 2009 Xinning Jiang vext@163.com
 *
 * All right reserved. Use is subject to license terms.
 * 
 *******************************************************************************
 */
package cn.ac.dicp.gp1809.proteome.IO.proteome.phospeptides;

/**
 * The Inspect phosphopeptide pair
 * 
 * @author Xinning
 * @version 0.1, 02-18-2009, 09:16:53
 */
public interface IInspectPhosphoPeptidePair extends IPhosPeptidePair {
	
	/**
	 * @return the expected value
	 */
	public double getMS2Pvalue();
	
	/**
	 * @return the expected value
	 */
	public double getMS3Pvalue();
	
	/**
	 * The merged evalue
	 * 
	 * @return
	 */
	public double getPvalue();

	/**
	 * @return the Ionscore
	 */
	public float getMS2MQScore();
	
	/**
	 * @return the Ionscore
	 */
	public float getMS3MQScore();
	
	/**
	 * The summed ion score
	 * 
	 * @return
	 */
	public float getMQScore();
	
}
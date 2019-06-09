/*******************************************************************************
 * Copyright 2019 The EVL authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eu.daproject.evl;

/**
 * Class definition for declaring the existence of the class Cases.
 * It is used to define anonymous classes where the match method is redefined for different tuple of classes.
 * Example:
 * <pre>
 * Method1<Integer> method = new Method1<Integer>().add(new Cases() {
 *
 *			int match(B obj) {
 *				return 1 + obj.getB();
 *			}
 *
 *			int match(C obj) {
 *				return 2 + obj.getC();
 *			}
 *		});
 * </pre>
 *
 */
public class Cases {

}
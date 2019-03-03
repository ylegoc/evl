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

import java.lang.invoke.MethodHandle;

import eu.daproject.evl.util.ClassTuple;

class Method {

	private MethodHandle methodHandle;
	private Object object;
	private ClassTuple tuple;
	private Comparable<?> data;
	private boolean lastAdded = false;
	
	Method(ClassTuple tuple, MethodHandle method, Object object) {
		this.methodHandle = method;
		this.object = object;
		this.tuple = tuple;
	}
	
	void setLastAdded(boolean value) {
		lastAdded = value;
	}
	
	boolean isLastAdded() {
		return lastAdded;
	}
	
	public MethodHandle getMethod() {
		return methodHandle;
	}
	
	public Object getObject() {
		return object;
	}
	
	public ClassTuple getClassTuple() {
		return tuple;
	}

	public void setData(Comparable<?> data) {
		this.data = data;
	}
	
	public Comparable<?> getData() {
		return data;
	}
	
}

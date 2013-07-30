package com.itface.star.system.develop.table.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itface.star.system.develop.table.model.FieldModel;
import com.itface.star.system.develop.table.model.TableModel;
import com.itface.star.system.develop.table.service.FieldModelService;
import com.itface.star.system.develop.table.service.ModelSourceService;
import com.itface.star.system.develop.table.service.TableModelService;
@Service
public class ModelSourceServiceImpl implements ModelSourceService{

	@Autowired
	private TableModelService tableModelService;
	@Autowired
	private FieldModelService fieldModelService;
	
	@Override
	public String genSource(long tableid) {
		// TODO Auto-generated method stub
		StringBuffer source = new StringBuffer();
		TableModel table= tableModelService.findTableById(tableid);
		List<FieldModel> fields = fieldModelService.findAll(tableid);
		if(table!=null){
			StringBuffer getSetMethodSource = new StringBuffer();
			String tablename = table.getName().trim();
			source.append("<pre>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>package</span> <span style='color: #000000;'>com.test;</span>").append("</br>");
			source.append("</br>");
			source.append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> java.io.Serializable;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> java.util.Date;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.Id;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.Temporal;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.TemporalType;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.CascadeType;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.Column;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.Entity;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.FetchType;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.GeneratedValue;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.GenerationType;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.Table;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.persistence.TableGenerator;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.validation.constraints.Max;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.validation.constraints.Min;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> javax.validation.constraints.Pattern;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> org.hibernate.validator.constraints.Length;</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>import</span> <span style='color: #000000;'> org.hibernate.validator.constraints.NotEmpty;</span>").append("</br>");
			source.append("</br>");
			source.append("</br>");
			source.append("<span style='color: #008000;'>/**</span>").append("</br>");
			source.append("</br>");
			source.append("<span style='color: #008000;'> *</span>").append("</br>");
			source.append("</br>");
			source.append("<span style='color: #008000;'> */</span>").append("</br>");
			source.append("</br>");
			source.append("<span style='color:#646464'>@Entity</span>").append("</br>");
			source.append("<span style='color:#646464'>@Table</span><span style='color: #000000;'>(name=\"bo_"+tablename+"\")</span>").append("</br>");
			source.append("<span style='color: #7f0055;font-weight: bold;'>public class</span> Bo"+tablename+" <span style='color: #7f0055;font-weight: bold;'>implements</span> Serializable{").append("</br>");
			source.append("	<span style='color:#646464'>@Id</span>").append("</br>");
			source.append("	<span style='color:#646464'>@TableGenerator</span>(name = \""+tablename+"_gen\",").append("</br>");
			source.append("		table = \"tb_generator\", ").append("</br>");
			source.append("		pkColumnName = \"gen_name\",").append("</br>");
			source.append("		valueColumnName = \"gen_value\",").append("</br>");
			source.append("		pkColumnValue = \""+tablename+"_pk\",").append("</br>");
			source.append("		initialValue = 100,").append("</br>");
			source.append("		allocationSize = 1").append("</br>");
			source.append("		)").append("</br>");
			source.append("	<span style='color:#646464'>@GeneratedValue</span>(strategy = GenerationType.TABLE, generator = \""+tablename+"_gen\")").append("</br>");
			source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> long id;").append("</br>");
			source.append("</br>");
			source.append("	<span style='color:#646464'>@Column</span>(name=\"bindid\")").append("</br>");
			source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> long bindid;").append("</br>");
			source.append("</br>");
			for(FieldModel f : fields){
				
				String name = f.getName().toLowerCase();
				String methodname = name.substring(0, 1).toUpperCase()+name.substring(1);
				String text = f.getText();
				int length = f.getFieldlength();
				int allownull = f.getAllownull();
				String type = f.getFieldtype();
				if("string".equals(type.toLowerCase())){
					if(allownull!=0){
						source.append("	<span style='color:#646464'>@NotEmpty</span>(message = \""+text+"不可以为空\")").append("</br>");
					}
					source.append("	<span style='color:#646464'>@Pattern</span>(regexp = \"[^'<>=\\\\]*\", message = \""+text+"不能包含特殊字符\")").append("</br>");
					source.append("	<span style='color:#646464'>@Length</span>(max="+length+",message=\""+text+"长度不能超过"+length+"\")").append("</br>");
					source.append("	<span style='color:#646464'>@Column</span>(name=\""+name+"\",length = "+length+")").append("</br>");
					source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> String "+name+";").append("</br>");
					source.append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> String get"+methodname+"(){").append("</br>");
					getSetMethodSource.append("		<span style='color: #7f0055;font-weight: bold;'>return</span> "+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> void set"+methodname+"(String "+name+"){").append("</br>");
					getSetMethodSource.append("		this."+name+"="+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					source.append("</br>");
				}else if("int".equals(type.toLowerCase())){
					source.append("	<span style='color:#646464'>@Max</span>(value="+length+",message=\""+text+"长度不能超过"+length+"\")").append("</br>");
					source.append("	<span style='color:#646464'>@Column</span>(name=\""+name+"\",length = "+length+")").append("</br>");
					source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> int "+name+";").append("</br>");
					source.append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> int get"+methodname+"(){").append("</br>");
					getSetMethodSource.append("		<span style='color: #7f0055;font-weight: bold;'>return</span> "+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> void set"+methodname+"(int "+name+"){").append("</br>");
					getSetMethodSource.append("		this."+name+"="+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					source.append("</br>");
				}else if("long".equals(type.toLowerCase())){
					source.append("	<span style='color:#646464'>@Max</span>(value="+length+",message=\""+text+"长度不能超过"+length+"\")").append("</br>");
					source.append("	<span style='color:#646464'>@Column</span>(name=\""+name+"\",length = "+length+")").append("</br>");
					source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> long "+name+";").append("</br>");
					source.append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> long get"+methodname+"(){").append("</br>");
					getSetMethodSource.append("		<span style='color: #7f0055;font-weight: bold;'>return</span> "+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> void set"+methodname+"(long "+name+"){").append("</br>");
					getSetMethodSource.append("		this."+name+"="+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					source.append("</br>");
				}else if("double".equals(type.toLowerCase())){
					source.append("	<span style='color:#646464'>@Max</span>(value="+length+",message=\""+text+"长度不能超过"+length+"\")").append("</br>");
					source.append("	<span style='color:#646464'>@Column</span>(name=\""+name+"\",length = "+length+")").append("</br>");
					source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> double "+name+";").append("</br>");
					source.append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> double get"+methodname+"(){").append("</br>");
					getSetMethodSource.append("		<span style='color: #7f0055;font-weight: bold;'>return</span> "+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> void set"+methodname+"(double "+name+"){").append("</br>");
					getSetMethodSource.append("		this."+name+"="+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					source.append("</br>");
				}else if("date".equals(type.toLowerCase())){
					source.append("	<span style='color:#646464'>@Temporal</span>(TemporalType.DATE)").append("</br>");
					source.append("	<span style='color:#646464'>@Column</span>(name=\""+name+"\")").append("</br>");
					source.append("	<span style='color: #7f0055;font-weight: bold;'>private</span> Date "+name+";").append("</br>");
					source.append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> Date get"+methodname+"(){").append("</br>");
					getSetMethodSource.append("		<span style='color: #7f0055;font-weight: bold;'>return</span> "+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					getSetMethodSource.append("	<span style='color: #7f0055;font-weight: bold;'>public</span> void set"+methodname+"(Date "+name+"){").append("</br>");
					getSetMethodSource.append("		this."+name+"="+name+";").append("</br>");
					getSetMethodSource.append("	}").append("</br>");
					source.append("</br>");
				}
			}
			source.append(getSetMethodSource).append("</br>");
			source.append("}").append("</br>");
			source.append("</pre>");
		}
		return source.toString();
	}

}

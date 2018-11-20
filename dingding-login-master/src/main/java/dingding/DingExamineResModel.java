package dingding;

/***
 * 钉钉 审批处理的返回处理model
 * 
 * @author bill.xu
 *
 */
public class DingExamineResModel {
	

	/***
	 * 审批的回调类型  bpms_instance_change 或者  bpms_task_change
	 */
	private String EventType;
	
	
	private String bizCategoryId;
	
	/***
	 * 账号信息 
	 */
	private String corpId;
	
	/***
	 * 审批提交时间
	 */
	private String createTime;
	
	/***
	 * 处理结束时间
	 */
	private String finishTime;
	
	/***
	 * 流程的查询 id  TODO 后期的流程可能还需要通过这个id 获取具体的审批单 进行其他的业务处理
	 * 
	 */
	private String processInstanceId;
	
	
	/***
	 * redirect 任务转交
	 * refuse  任务拒绝
	 */
	private String result;
	
	/***
	 * 任务拒绝的理由
	 */
	private String remark;
	
	/***
	 * 发起人的userid
	 */
	private String staffId;
	
	/**
	 * 任务的标题
	 */
	private String title;
	
	/***
	 * finish  处理完成，任务转交，结束|终止
	 * start  任务审批开始
	 * 
	 */
	private String type;
	
	/***
	 * 连接url
	 */
	private String url;

	public String getEventType() {
		return EventType;
	}

	public void setEventType(String eventType) {
		EventType = eventType;
	}

	public String getBizCategoryId() {
		return bizCategoryId;
	}

	public void setBizCategoryId(String bizCategoryId) {
		this.bizCategoryId = bizCategoryId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}

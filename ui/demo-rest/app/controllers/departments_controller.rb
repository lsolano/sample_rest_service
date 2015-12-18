class DepartmentsController < ApplicationController
  before_action :set_department, only: [:show, :edit, :update, :destroy]
  skip_before_filter :verify_authenticity_token  
  API_SERVER="http://40.76.93.236:8080"

  # GET /departments
  # GET /departments.json
  def index
    request = RestClient.get File.join(API_SERVER,"rest-api/departments")
  
     department = Struct.new(:description, :name, :id)
     @departments = []

     JSON.parse(request).each do |dpto|
    
     Rails.logger.debug "ERROR: #{dpto}" 
     new_dpto = department.new
 
     new_dpto.id = dpto['id']
     new_dpto.name = dpto['name']
     new_dpto.description = dpto['description']

     @departments << new_dpto
    end

  end

  # GET /departments/1
  # GET /departments/1.json
  def show
  end

  # GET /departments/new
  def new
    @department = Department.new
  end

  # GET /departments/1/edit
  def edit
  end

  # POST /departments
  # POST /departments.json
  def create

    request = RestClient.post File.join(API_SERVER,"rest-api/departments"), { 
            'name' => params['department']['name'], 
            'description' => params['department']['description'] }.to_json, :content_type => :json, :accept => :json
    redirect_to :action => :index
  end

  # PATCH/PUT /departments/1
  # PATCH/PUT /departments/1.json
  def update
    request = RestClient.put File.join(API_SERVER,"rest-api/departments"), { 
       'id' =>  params['id'], 
       'name' =>  params['department']['name'], 
       'description' =>  params['department']['description'] }.to_json, :content_type => :json, :accept => :json

    redirect_to :action => :index
  end

  def delete
    request = RestClient.delete File.join(API_SERVER,"rest-api/departments",params['id'])
    redirect_to :action => :index	
  end

  # DELETE /departments/1
  # DELETE /departments/1.json
  def destroy
    request = RestClient.delete File.join(API_SERVER,"rest-api/departments",params['id'])
    redirect_to :action => :index	
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_department
      request = RestClient.get File.join(API_SERVER,"rest-api/departments",params['id'])

      data  = JSON.parse(request)

      department = Struct.new(:description, :name, :id)
      @department = department.new
      @department.id = data['id']
      @department.name = data["name"]
      @department.description = data['description']

    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def department_params
      params.require(:department).permit(:id, :name, :description)
    end
end


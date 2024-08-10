import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../Services/snackbar.service';
import { UserService } from '../Services/user.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.scss']
})
export class ForgetPasswordComponent implements OnInit {
  forgetPasswordForm:any = FormGroup;
  responseMessage:any;

  constructor(
    private formBuilder:FormBuilder,
    private userService:UserService,
    public dialogRef:MatDialogRef<ForgetPasswordComponent>,
    private ngxService:NgxUiLoaderService,
    private snackbarService:SnackbarService
  ) { }

  ngOnInit(): void {
    this.forgetPasswordForm=this.formBuilder.group({
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]]
    });
  }

  handleSubmit(){
    this.ngxService.start();
    var formData = this.forgetPasswordForm.value;
    var data = {
      email:formData.email
    }
    this.userService.forgetPassword(data).subscribe((response:any)=>{
      this.ngxService.stop();
      this.responseMessage = response?.message;
      this.dialogRef.close();
      this.snackbarService.openSnackBar(this.responseMessage,"");
    },(error)=>{
      this.ngxService.stop();
      if(error.error?.message){
        this.responseMessage = GlobalConstants.genericError;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })

  }

}

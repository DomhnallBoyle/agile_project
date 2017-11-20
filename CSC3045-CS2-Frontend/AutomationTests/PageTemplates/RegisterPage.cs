using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class RegisterPage : BasePage
    {
        public TextBox EmailTextBox
        {
            get { return MainWindow.Get<TextBox>("EmailTextBox"); }
        }
        public TextBox FirstnameTextBox
        {
            get { return MainWindow.Get<TextBox>("FirstnameTextBox"); }
        }
        public TextBox SurnameTextBox
        {
            get { return MainWindow.Get<TextBox>("SurnameTextBox"); }
        }
        public TextBox PasswordTextBox
        {
            get { return MainWindow.Get<TextBox>("PasswordTextBox"); }
        }
        public TextBox ConfirmPasswordTextBox
        {
            get { return MainWindow.Get<TextBox>("ConfirmPasswordTextBox"); }
        }
        public CheckBox ProductOwnerCheckBox
        {
            get { return MainWindow.Get<CheckBox>("ProductOwnerCheckBox"); }
        }
        public CheckBox ScrumMasterCheckBox
        {
            get { return MainWindow.Get<CheckBox>("ScrumMasterCheckBox"); }
        }
        public CheckBox DeveloperCheckBox
        {
            get { return MainWindow.Get<CheckBox>("DeveloperCheckBox"); }
        }
        public Button RegisterButton
        {
            get { return MainWindow.Get<Button>("RegisterButton"); }
        }

        public RegisterPage(Window window) : base (window)
        {

        }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Registration");
        }
    }
}

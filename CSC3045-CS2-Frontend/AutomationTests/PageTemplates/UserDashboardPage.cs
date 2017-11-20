using AutomationTests.Util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class UserDashboardPage : BasePage
    {
        public Button CreateProjectButton
        {
            get { return MainWindow.Get<Button>("CreateProjectButton"); }
        }
        public Button LogoutButton
        {
            get { return MainWindow.Get<Button>("LogoutButton"); }
        }
        public ListBox ProjectListBox
        {
            get { return MainWindow.Get<ListBox>("ProjectListBox"); }
        }

        public UserDashboardPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("User Dashboard");
        }

        public void DismissNoProjectsMsgBox()
        {
            var messageBox = MainWindow.MessageBox("Info");

            if (messageBox != null)
            {
                MessageBoxUtil.ClickOKButton(messageBox);
            }
        }
    }
}

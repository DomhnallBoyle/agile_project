using AutomationTests.Util;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.WPFUIItems;

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

        public Image ManagerIcon(WPFListItem listItem)
        {
            return listItem.Get<Image>("Manager");
        }

        public Image DeveloperIcon(WPFListItem listItem)
        {
            return listItem.Get<Image>("Developer");
        }

        public Image ScrumMaserIcon(WPFListItem listItem)
        {
            return listItem.Get<Image>("ScrumMaster");
        }

        public Image ProductOwnerIcon(WPFListItem listItem)
        {
            return listItem.Get<Image>("ProductOwner");
        }
    }
}

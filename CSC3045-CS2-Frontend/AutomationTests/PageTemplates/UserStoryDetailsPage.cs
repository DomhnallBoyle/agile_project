﻿using AutomationTests.Util;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.PageTemplates
{
    class UserStoryDetailsPage : BasePage
    {
        public Button CreateAcceptanceTestButton
        {
            get { return MainWindow.Get<Button>("CreateAcceptanceTestButton"); }
        }

        public Button BackButton
        {
            get { return MainWindow.Get<Button>("BackButton"); }
        }
        
        public ListBox UserStoryAcceptanceTests
        {
            get { return MainWindow.Get<ListBox>("UserStoryAcceptanceTests"); }
        }

        public Label StoryNameTextBlock
        {
            get { return MainWindow.Get<Label>("StoryNameTextBlock"); }
        }

        public Label StoryMarketValueTextBlock
        {
            get { return MainWindow.Get<Label>("StoryMarketValueTextBlock"); }
        }

        public Label StoryPointsTextBlock
        {
            get { return MainWindow.Get<Label>("StoryPointsTextBlock"); }
        }

        public CheckBox CompleteLabelBlock
        {
            get { return MainWindow.Get<CheckBox>("CompleteLabelBlock"); }
        }
     
        public UserStoryDetailsPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("User Story Details");
        }

    }


}
